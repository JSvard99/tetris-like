package com.dt181g.project;

import com.dt181g.project.controllers.GameController;
import javax.swing.*;

/**
 * The main starting point for Project Assignment.
 * @author Johan Svärd
 */
public final class Project {
    private Project() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method starting point. Created a new GameController.
     * @param args command arguments.
     */
    public static void main(final String... args) {
        SwingUtilities.invokeLater(GameController::new);
    }
}
