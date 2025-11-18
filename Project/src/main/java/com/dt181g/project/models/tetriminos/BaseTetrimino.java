package com.dt181g.project.models.tetriminos;

import com.dt181g.project.TetriminoColor;
import java.util.ArrayList;
import java.util.List;

/**
 * The BaseTetrimino class, an abstract class for the tetriminos.
 * @author Johan Svärd
 */
public abstract class BaseTetrimino {

    static int numberOfPieces = 0;
    int id;
    List<int[][]> shapes = new ArrayList<>(); //Include all rotations.
    int[] position = new int[2]; // The y,x coordinate for the top left corner of the shape array.
    int currentShapeIndex = 0;
    TetriminoColor color;

    /**
     * The constrcutor for the BaseTetrimino class.
     */
    public BaseTetrimino() {
        initializeTetrimino();
    }

    /**
     * Used when creating new tetriminos, a template method that sets the id but let the shapes and color be set by
     * subclasses.
     */
    protected void initializeTetrimino() {
        setId();
        setShapes();
        setColor();
    }

    private void setId() {
        numberOfPieces++;
        id = numberOfPieces;
    }

    /**
     * Sets the shapes/rotations of this piece, part of the template method.
     */
    protected abstract void setShapes();

    /**
     * Sets the color of this piece, part of the template method.
     */
    protected abstract void setColor();

    /**
     * Gets the current shape/rotation of this piece.
     * @return the current shape/rotation.
     */
    public int[][] getTetriminoShape() {
        return shapes.get(currentShapeIndex);
    }

    /**
     * Peek into what the next shape/rotation is.
     * @return
     */
    public int[][] peekNextTetriminoShape() {
        if (currentShapeIndex + 1 == shapes.size()) {
            return shapes.get(0);
        }
        else {
            return shapes.get(currentShapeIndex + 1);
        }
    }

    /**
     * Rotate this piece.
     */
    public void rotate() {
        if (currentShapeIndex + 1 == shapes.size()) {
            currentShapeIndex = 0;
        }
        else {
            currentShapeIndex += 1;
        }
    }

    /**
     * Move the piece right.
     */
    public void moveRight() {
        position[1] += 1;
    }

    /**
     * Move the piece left.
     */
    public void moveLeft() {
        position[1] -= 1;
    }

    /**
     * Move the piece down.
     */
    public void moveDown() {
        position[0] += 1;
    }

    /**
     * Sets the position of this piece, the y and x cordinate is based in the top left corner of the shape.
     * @param position the position that the piece will be set to.
     */
    public void setPosition(int[] position) {
        this.position = position;
    }

    /**
     * Gets the position of this piece, the y and x cordinate is based in the top left corner of the shape.
     * @return
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Gets the id of this piece.
     * @return the id of this piece.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the color of this piece.
     * @return the color of this piece, represented as a TetriminoColor enum.
     */
    public TetriminoColor getColor() {
        return color;
    }
}
