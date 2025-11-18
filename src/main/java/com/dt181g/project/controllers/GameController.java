package com.dt181g.project.controllers;

import com.dt181g.project.TetriminoColor;
import com.dt181g.project.models.GameModel;
import com.dt181g.project.models.tetriminos.BaseTetrimino;
import com.dt181g.project.views.GameView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GameController class which acts as the controller of the MVC-pattern for the tetris game.
 * @author Johan Svärd
 */
public class GameController {

    GameModel model = new GameModel();
    GameView view = new GameView();
    Timer updateViewTimer;

    /**
     * The constructor for the GameController class. Updates the initial view and starts the game loop.
     */
    public GameController() {

        SwingUtilities.invokeLater(() -> {

            view.requestFocusInWindow();  // Otherwise pause button has focus and key inputs don't register.

            view.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {

                    if (!model.isPaused()) {

                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            model.moveCurrentPieceLeft();
                            updateView();
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            model.moveCurrentPieceRight();
                            updateView();
                        }
                        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            model.moveCurrentPieceDown();
                            updateView();
                        }

                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                    if (!model.isPaused()) {

                        if (e.getKeyCode() == KeyEvent.VK_UP) {
                            model.rotateCurrentPiece(3);
                            updateView();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                            model.dropPiece();
                            updateView();
                        }

                    }
                }
            });

            view.addPauseButtonActionListener(e -> {
                switchPauseGame();
                view.switchPauseButtonText(model.isPaused());
                view.requestFocusInWindow();
            });

            updateViewTimer= new Timer(100, e -> {
                updateView();
                if (model.isGameOver()) {
                    stopGame();
                }
            });

            updateView();
            startGame();

        });
    }

    private void updateView() {
        SwingUtilities.invokeLater( () -> {
            view.displayGameBoard(model.getGameBoard(), tetriminoColorMap(model.getAllPieces()),
                    model.getNextTetrimino().getTetriminoShape(), model.getNextTetrimino().getColor(),
                    model.getScore(), model.getLinesCleared(), model.getLevel());
                }
        );

    }

    private Map<Integer, TetriminoColor> tetriminoColorMap(List<BaseTetrimino> tetriminos) {

        Map<Integer, TetriminoColor> colorMap = new HashMap<>();

        for (BaseTetrimino tetrimino: tetriminos) {
            colorMap.put(tetrimino.getId(), tetrimino.getColor());
        }

        return colorMap;
    }

    private void switchPauseGame() {
        model.switchPause();
    }

    private void startGame() {

        model.resetGame();
        updateView();
        model.setGameOver(false);

        new Thread(model).start();
        updateViewTimer.start();
    }

    private void stopGame() {
        updateViewTimer.stop();
        switchPauseGame();
        view.showGameOver();
        ActionListener newGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                view.removePauseButtonActionListener(this);
            }
        };
        view.addPauseButtonActionListener(newGame);
    }
}
