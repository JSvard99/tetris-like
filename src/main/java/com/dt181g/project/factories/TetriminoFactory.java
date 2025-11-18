package com.dt181g.project.factories;

import com.dt181g.project.observer.Observer;
import com.dt181g.project.models.GameModel;
import com.dt181g.project.models.tetriminos.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The TetriminoFactory class, which is an abstract factory for creating tetriminos. Acts as an observer to the game
 * model.
 * @author Johan Svärd
 */
public class TetriminoFactory implements Observer {

    GameModel model;

    /**
     * The constructor for the TetriminoFactory.
     * @param model the model for the game, which handles tetriminos.
     */
    public TetriminoFactory(GameModel model) {
        this.model = model;
        model.attach(this);
    }

    /**
     * Updates this observer. Creates a new tetrimino set if needed.
     */
    @Override
    public void update() {

        if (model.getNextTetriminos().size() > 5) {
            return;
        }

        List<BaseTetrimino> createdTetriminos = new ArrayList<>();
        createdTetriminos.addAll(createTetriminoSet());
        createdTetriminos.addAll(createTetriminoSet());
        Collections.shuffle(createdTetriminos);
        model.getNextTetriminos().addAll(createdTetriminos);
    }

    private List<BaseTetrimino> createTetriminoSet() {
        List<BaseTetrimino> createdTetriminos = new ArrayList<>();
        createdTetriminos.add(new IShapeTetrimino());
        createdTetriminos.add(new JShapeTetrimino());
        createdTetriminos.add(new LShapeTetrimino());
        createdTetriminos.add(new OShapeTetrimino());
        createdTetriminos.add(new SShapeTetrimino());
        createdTetriminos.add(new TShapeTetrimino());
        createdTetriminos.add(new ZShapeTetrimino());
        return createdTetriminos;
    }
}
