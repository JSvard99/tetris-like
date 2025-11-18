package com.dt181g.project.models.tetriminos;

import com.dt181g.project.TetriminoColor;

/**
 * The SShapeTetrimino class which is a subclass of the BaseTetrimino class.
 * @author Johan Svärd
 */
public class SShapeTetrimino extends BaseTetrimino {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setShapes() {

        shapes.add(new int[][] {
                {0, 0,  id, id},
                {0, id, id, 0},
                {0, 0,  0,  0},
                {0, 0,  0,  0}});

        shapes.add(new int[][] {
                {0, 0, id, 0},
                {0, 0, id, id},
                {0, 0, 0,  id},
                {0, 0, 0,  0}});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setColor() {
        color = TetriminoColor.GREEN;
    }
}
