package com.dt181g.project.views;

import com.dt181g.project.TetriminoColor;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The GameView class, used as the view of the MVC-pattern of the game, uses swing and extends JFrame.
 * @author Johan Svärd
 */
public class GameView extends JFrame {

    JPanel infoPanel = new JPanel(new GridBagLayout());
    JLabel scoreTitle = new JLabel("SCORE");
    JLabel scoreLabel = new JLabel();
    JLabel levelTitle = new JLabel("LEVEL");
    JLabel levelLabel = new JLabel();
    JLabel linesClearedTitle = new JLabel("LINES CLEARED");
    JLabel linesClearedLabel = new JLabel();
    JPanel gameBoard = new JPanel(new GridLayout(20, 10));
    ArrayList<JPanel> gameBoardPanels = new ArrayList<>();
    JPanel nextPiecePanel = new JPanel(new GridBagLayout());
    ArrayList<JPanel> nextPiecePanels = new ArrayList<>();
    JLabel nextPieceTitle = new JLabel("NEXT PIECE");
    JPanel nextPieceLabel = new JPanel(new GridLayout(4, 4));
    JButton pauseButton = new JButton("Pause");
    JPanel instructionsPanel = new JPanel(new GridBagLayout());
    JLabel moveLeftInstruction = new JLabel("Move left: Left arrow key");
    JLabel moveRightInstruction = new JLabel("Move right: Right arrow key");
    JLabel moveDownInstruction = new JLabel("Move down: Down arrow key");
    JLabel dropPieceInstruction = new JLabel("Drop: Spacebar");
    Map<TetriminoColor, Color> colorMap = new HashMap<>();

    /**
     * The public constructor of the GameView class, sets the initial layout of this frame.
     */
    public GameView() {

        colorMap.put(TetriminoColor.LIGHT_BLUE, Color.CYAN);
        colorMap.put(TetriminoColor.YELLOW, Color.YELLOW);
        colorMap.put(TetriminoColor.PURPLE, Color.MAGENTA);
        colorMap.put(TetriminoColor.BLUE, Color.BLUE);
        colorMap.put(TetriminoColor.ORANGE, Color.ORANGE);
        colorMap.put(TetriminoColor.GREEN, Color.GREEN);
        colorMap.put(TetriminoColor.RED, Color.RED);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.PINK);


        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.setBorder(new LineBorder(Color.WHITE, 10, true));
        infoPanel.setPreferredSize(new Dimension(200, 200));
        scoreTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoPanel.add(scoreTitle,
                setGridBagConstraints(0, 0, 1, 1, 1, 1 ,0));
        scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoPanel.add(scoreLabel,
                setGridBagConstraints(0, 1, 1, 1, 1, 1, 0));
        levelTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoPanel.add(levelTitle,
                setGridBagConstraints(0, 2, 1, 1, 1, 1, 0));
        levelLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoPanel.add(levelLabel,
                setGridBagConstraints(0, 3, 1, 1, 1, 1, 0));
        linesClearedTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoPanel.add(linesClearedTitle,
                setGridBagConstraints(0, 4, 1, 1, 1, 1, 0));
        linesClearedLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        infoPanel.add(linesClearedLabel,
                setGridBagConstraints(0, 5, 1, 1, 1, 1, 0));


        gameBoard.setBackground(Color.GRAY);
        gameBoard.setBorder(new LineBorder(Color.WHITE, 10, true));
        gameBoard.setPreferredSize(new Dimension(300, 600));
        for (int i = 0; i < 200; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.DARK_GRAY);
            panel.setBorder(new LineBorder(Color.GRAY));
            gameBoard.add(panel);
            gameBoardPanels.add(panel);
        }

        nextPiecePanel.setBackground(Color.LIGHT_GRAY);
        nextPiecePanel.setBorder(new LineBorder(Color.WHITE, 10, true));
        nextPiecePanel.setPreferredSize(new Dimension(150, 150));
        nextPieceTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        nextPiecePanel.add(nextPieceTitle,
                setGridBagConstraints(0, 0, 1, 1, 1, 1, 0));
        nextPieceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        for (int i = 1; i <= 16; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.DARK_GRAY);
            panel.setBorder(new LineBorder(Color.GRAY));
            nextPieceLabel.add(panel);
            nextPiecePanels.add(panel);
        }
        nextPiecePanel.add(nextPieceLabel,
                setGridBagConstraints(0, 1, 1, 1, 1, 1, 0));

        instructionsPanel.setBackground(Color.LIGHT_GRAY);
        instructionsPanel.setBorder(new LineBorder(Color.WHITE, 10, true));
        instructionsPanel.setPreferredSize(new Dimension(200, 200));
        instructionsPanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        instructionsPanel.add(moveLeftInstruction,
                setGridBagConstraints(0, 0, 1, 1, 1, 1, 0));
        instructionsPanel.add(moveRightInstruction,
                setGridBagConstraints(0, 1, 1, 1, 1, 1, 0));
        instructionsPanel.add(moveDownInstruction,
                setGridBagConstraints(0, 2, 1, 1, 1, 1, 0));
        instructionsPanel.add(dropPieceInstruction,
                setGridBagConstraints(0, 3, 1, 1, 1, 1, 0));

        pauseButton.setBackground(Color.WHITE);
        pauseButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));


        add(infoPanel,
                setGridBagConstraints(0, 1, 1, 1, 1, 1, 50));
        add(gameBoard,
                setGridBagConstraints(1, 0, 1, 2, 1, 1, 0));
        add(nextPiecePanel,
                setGridBagConstraints(2, 0, 1, 1, 1, 1, 75));
        add(pauseButton,
                setGridBagConstraints(0, 0, 1, 1, 1, 1, 0));
        add(instructionsPanel,
                setGridBagConstraints(2, 1, 1, 1, 1, 1, 75));

        pack();
        setVisible(true);
    }

    private GridBagConstraints setGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight,
                                                    int weightx, int weighty, int inset) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.insets = new Insets(inset, inset, inset, inset);

        return gbc;
    }

    /**
     * Adds an action listener to the pause button.
     * @param listener the listener to be added.
     */
    public void addPauseButtonActionListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    /**
     * Removes an action listener from the pause button.
     * @param listener the listener to be removed.
     */
    public void removePauseButtonActionListener(ActionListener listener) {
        pauseButton.removeActionListener(listener);
    }

    /**
     * Switches the pause button text from "Pause" ot "Remove" based on if the game is paused or not.
     * @param isPaused if the game is paused or not.
     */
    public void switchPauseButtonText(boolean isPaused) {
        if (isPaused) {
            pauseButton.setText("Resume");
        }
        else {
            pauseButton.setText("Pause");
        }
    }

    /**
     * Show a popup that states that the game is over.
     */
    public void showGameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!");
        pauseButton.setText("New Game");
    }

    /**
     * Used to display the current game board to the user.
     * @param gameBoard the game board as a 2d array.
     * @param TetriminosColor a map of the colors of the teriminos where the key is the id of the tetrimino.
     * @param nextTetriminoShape the shape of the upcoming tetrimino.
     * @param nextTetriminoColor the color of the upcoming tetrimino.
     * @param score the current score.
     * @param linesCleared the current lines cleared.
     * @param level the current level.
     */
    public void displayGameBoard(int[][] gameBoard, Map<Integer, TetriminoColor> TetriminosColor, int[][] nextTetriminoShape,
                                 TetriminoColor nextTetriminoColor, int score, int linesCleared, int level) {


        scoreLabel.setText(String.valueOf(score));
        linesClearedLabel.setText(String.valueOf(linesCleared));
        levelLabel.setText(String.valueOf(level));

        ArrayList<Integer> flatGameBoard = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                flatGameBoard.add(gameBoard[i][j]);
            }
        }

        for (int i = 0; i < gameBoardPanels.size(); i++) {

            if (flatGameBoard.get(i) != 0) {
                gameBoardPanels.get(i).setBackground(colorMap.get(TetriminosColor.get(flatGameBoard.get(i))));
            }
            else {
                gameBoardPanels.get(i).setBackground(Color.DARK_GRAY);
            }
        }

        ArrayList<Integer> flatNextPieceShape = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flatNextPieceShape.add(nextTetriminoShape[i][j]);
            }
        }

        for (int i = 0; i < nextPiecePanels.size(); i++) {
            if (flatNextPieceShape.get(i) != 0) {
                nextPiecePanels.get(i).setBackground(colorMap.get(nextTetriminoColor));
            }
            else {
                nextPiecePanels.get(i).setBackground(Color.DARK_GRAY);
            }
        }
    }
}
