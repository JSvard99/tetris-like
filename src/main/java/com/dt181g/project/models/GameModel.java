package com.dt181g.project.models;

import com.dt181g.project.observer.Observable;
import com.dt181g.project.factories.TetriminoFactory;
import com.dt181g.project.models.tetriminos.BaseTetrimino;
import com.dt181g.project.observer.Observer;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The GameModel class used as the model in the MVC-pattern, can be observed and implements Runnable as the game loop.
 * @author Johan Svärd
 */
public class GameModel implements Observable, Runnable {

    List<Observer> observers = new ArrayList<>();
    Deque<BaseTetrimino> nextTetriminos = new LinkedBlockingDeque<>();
    BaseTetrimino currentPiece;
    List<BaseTetrimino> allPieces = new CopyOnWriteArrayList<>();
    int[][] gameBoard = new int[20][10]; // The Board has 20 rows and 10 columns.
    final Object gameBoardLock = new Object(); // Used to synchronize gameBoard.
    int tickLengthMs = 2000;
    int score = 0;
    int level = 1;
    int linesCleared = 0;
    AtomicBoolean isGameOver = new AtomicBoolean(false);
    AtomicBoolean isPaused = new AtomicBoolean(false);

    /**
     * The constructor for the GameModel. Creates a new factory and sets the intital game board.
     */
    public GameModel() {

        new TetriminoFactory(this);

        notifyObservers();

        changeCurrentPiece();

        updateGameBoard();
    }

    /**
     * Gets the game board as a 2d array.
     * @return the game board as a 2d array.
     */
    public int[][] getGameBoard() {

        updateGameBoard();
        return gameBoard;
    }

    /**
     * Gets the current score.
     * @return the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the number of lines cleared.
     * @return the number of lines cleared.
     */
    public int getLinesCleared() {
        return linesCleared;
    }

    /**
     * Gets the current level.
     * @return the current level.
     */
    public int getLevel() {
        return level;
    }

    private void updateGameBoard() {

        synchronized (gameBoardLock) {

            clearCurrentPiece();

            int[] piecePos = currentPiece.getPosition();
            int[][] pieceShape = currentPiece.getTetriminoShape();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++)  {

                    if (pieceShape[i][j] != 0) {
                        gameBoard[i + piecePos[0]][j + piecePos[1]] = currentPiece.getId();
                    }
                }
            }
        }

    }

    private void clearCurrentPiece() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameBoard[i][j] == currentPiece.getId()) {
                    gameBoard[i][j] = 0;
                }
            }
        }
    }

    /**
     * Gets all the tetriminos that have appeared on the game board.
     * @return all the pieces.
     */
    public List<BaseTetrimino> getAllPieces() {
        return allPieces;
    }

    /**
     * Gets the upcoming tetriminos.
     * @return the upcoming tetriminos.
     */
    public Deque<BaseTetrimino> getNextTetriminos() {
        return nextTetriminos;
    }

    /**
     * Gets the next tetrimino.
     * @return the next tetrimino.
     */
    public BaseTetrimino getNextTetrimino() {
        return nextTetriminos.peekFirst();
    }

    /**
     * Rotates the current piece if possible.
     * @param maxRecursionDepth used to ensure it does not recurse infinitely, should preferably be 3.
     */
    public void rotateCurrentPiece(int maxRecursionDepth) {

        if (maxRecursionDepth < 1) {
            return;
        }

        synchronized (gameBoardLock) {

            boolean rotationPerformed = false;
            int[] piecePos = currentPiece.getPosition();
            int[][] rotationShape = currentPiece.peekNextTetriminoShape();

            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {

                    if (rotationShape[row][column] != 0) {
                        try {
                            if (gameBoard[row + piecePos[0]][column + piecePos[1]] != 0 && gameBoard[row + piecePos[0]][column + piecePos[1]] != currentPiece.getId()) {

                                if (piecePos[1] > 0) {
                                    moveCurrentPieceLeft();
                                    rotateCurrentPiece(maxRecursionDepth - 1);
                                    rotationPerformed = true;
                                }
                                else {
                                    moveCurrentPieceRight();
                                    rotateCurrentPiece(maxRecursionDepth - 1);
                                    rotationPerformed = true;
                                }
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e) {

                            if (piecePos[1] > 0) {
                                moveCurrentPieceLeft();
                                rotateCurrentPiece(maxRecursionDepth - 1);
                                rotationPerformed = true;
                            }
                            else {
                                moveCurrentPieceRight();
                                rotateCurrentPiece(maxRecursionDepth - 1);
                                rotationPerformed = true;
                            }
                        }
                    }
                }
            }
            if (!rotationPerformed) {
                currentPiece.rotate();
            }
        } //Synchronized block end.
    }

    /**
     * Moves the current piece right if possible.
     */
    public void moveCurrentPieceRight() {

        synchronized (gameBoardLock) {

            int[] piecePos = currentPiece.getPosition();
            int[][] pieceShape = currentPiece.getTetriminoShape();

            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {

                    if (pieceShape[row][column] != 0) {
                        try {
                            if (gameBoard[row + piecePos[0]][column + piecePos[1] + 1] != 0 && gameBoard[row + piecePos[0]][column + piecePos[1] + 1] != currentPiece.getId()) {
                                return;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                            return;
                        }
                    }
                }
            }
            currentPiece.moveRight();
        }
    }

    /**
     * Moves the current piece left if possible.
     */
    public void moveCurrentPieceLeft() {

        synchronized (gameBoardLock) {

            int[] piecePos = currentPiece.getPosition();
            int[][] pieceShape = currentPiece.getTetriminoShape();

            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {

                    if (pieceShape[row][column] != 0) {
                        try {
                            if (gameBoard[row + piecePos[0]][column + piecePos[1] - 1] != 0 && gameBoard[row + piecePos[0]][column + piecePos[1] - 1] != currentPiece.getId()) {
                                return;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                            return;
                        }
                    }
                }
            }
            currentPiece.moveLeft();
        }
    }

    /**
     * Move the current piece down if possible, otherwise change the current piece.
     */
    public void moveCurrentPieceDown() {

        synchronized (gameBoardLock) {
            int[] piecePos = currentPiece.getPosition();
            int[][] pieceShape = currentPiece.getTetriminoShape();

            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {

                    if (pieceShape[row][column] != 0) {
                        try {
                            if (gameBoard[row + piecePos[0] + 1][column + piecePos[1]] != 0 && gameBoard[row + piecePos[0] + 1][column + piecePos[1]] != currentPiece.getId()) {
                                checkClearLines(piecePos[0]);
                                changeCurrentPiece();
                                return;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                            checkClearLines(piecePos[0]);
                            changeCurrentPiece();
                            return;
                        }
                    }
                }
            }
            currentPiece.moveDown();
        }
    }

    /**
     * Drops the piece as far as it goes.
     */
    public void dropPiece() {

        int id = currentPiece.getId();

        while (id == currentPiece.getId()) {
            moveCurrentPieceDown();
            updateGameBoard();
        }
    }

    private void changeCurrentPiece() {

        synchronized (gameBoardLock) {

            currentPiece = nextTetriminos.pop();
            int[][] currentPieceShape = currentPiece.getTetriminoShape();
            allPieces.add(currentPiece);
            currentPiece.setPosition(new int[]{0, 3});

            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {

                    if (currentPieceShape[row][column] != 0) {

                        if (gameBoard[row][column + 3] != 0) {
                            isGameOver.set(true);
                            return;
                        }
                    }
                }
            }

            notifyObservers();
        }
    }

    private void checkClearLines(int piecePosRow) {

        int linesClearedThisTurn = 0;

        for (; piecePosRow < 20 || piecePosRow > piecePosRow + 4; piecePosRow++) {

            boolean doClearLine = true;

            for (int column = 0; column < 10; column++) {
                if (gameBoard[piecePosRow][column] == 0) {
                    doClearLine = false;
                    break;
                }
            }
            if (doClearLine) {
                clearLine(piecePosRow);
                linesClearedThisTurn++;
                linesCleared++;
                if (linesCleared % 10 == 0) {
                    increaseLevel();
                }
            }
        }
        updateScore(linesClearedThisTurn);
    }

    private void clearLine(int row) {

        for (int column = 0; column < 10; column++) {
            gameBoard[row][column] = 0;
        }

        for (; row > 0; row--) {
            for (int column = 0; column < 10; column++) {
                gameBoard[row][column] = gameBoard[row - 1][column];
            }
        }
    }

    private void increaseLevel() {
        if (level < 16) {
            level++;
            tickLengthMs -= 125;
        }
    }

    private void updateScore(int linesCleared) {

        if (linesCleared == 1) {
            score += 100;
        }
        else if (linesCleared == 2) {
            score += 300;
        }
        else if (linesCleared == 3) {
            score += 600;
        }
        else if (linesCleared == 4) {
            score += 1000;
        }
    }

    /**
     * Switch the game from paused to not pause or vice versa.
     */
    public void switchPause() {
        isPaused.set(!isPaused.get());
    }

    /**
     * Checks if the game is paused or not.
     * @return a boolean if the game is paused.
     */
    public boolean isPaused() {
        return isPaused.get();
    }

    /**
     * Checks if the game is over.
     * @return a boolean if the game is over.
     */
    public boolean isGameOver() {
        return isGameOver.get();
    }

    /**
     * Sets the game over boolean.
     * @param isGameOver if the game should be over or not.
     */
    public void setGameOver(boolean isGameOver) {
        this.isGameOver.set(isGameOver);
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        score = 0;
        linesCleared = 0;
        level = 1;
        allPieces.clear();
        nextTetriminos.clear();
        notifyObservers();
        resetGameBoard();
        changeCurrentPiece();
    }

    private void resetGameBoard() {

        synchronized (gameBoardLock) {

            for (int row = 0; row < 20; row++) {
                for (int column = 0; column < 10; column++) {

                    gameBoard[row][column] = 0;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update();
        }
    }

    /**
     * Runs the game loop.
     */
    @Override
    public void run() {

        while (!isGameOver.get()) {

            try {
                Thread.sleep(tickLengthMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isPaused.get()) {
                continue;
            }

            moveCurrentPieceDown();
        }
    }
}
