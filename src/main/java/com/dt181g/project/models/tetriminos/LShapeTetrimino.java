package com.dt181g.project.models.tetriminos;

import com.dt181g.project.TetriminoColor;

/**
 * The LShapeTetrimino class which is a subclass of the BaseTetrimino class.
 * @author Johan Svärd
 */
public class LShapeTetrimino extends BaseTetrimino {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setShapes() {

        shapes.add(new int[][] {
                {0, 0,  0,  id},
                {0, id, id, id},
                {0, 0,  0,  0},
                {0, 0,  0,  0}});

        shapes.add(new int[][] {
                {0, 0, id, 0},
                {0, 0, id, 0},
                {0, 0, id, id},
                {0, 0, 0,  0}});

        shapes.add(new int[][] {
                {0, id, id, id},
                {0, id, 0,  0},
                {0, 0,  0,   0},
                {0, 0,  0,  0}});

        shapes.add(new int[][] {
                {0, id, id, 0},
                {0, 0,  id, 0},
                {0, 0,  id, 0},
                {0, 0,  0,  0}});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setColor() {
        color = TetriminoColor.ORANGE;
    }
}
