package com.tractor;

/**
 * добавлено поле с типом интерфейса IStateOrient (состояние)
 */
public class Tractor {

    private int[] position = new int[]{0, 0};
    private int[] field = new int[]{5, 5};
    Orientation orientation = Orientation.NORTH;

    private IStateOrient stateOrient = new NorthState();

    public void setStateOrient(IStateOrient stateOrient) {
        this.stateOrient = stateOrient;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPosition() {
        return position;
    }

    public int[] getField() {
        return field;
    }

    /**
     *  при движении используем поле с типом интерфейса IStateOrient (состояние)
     *  вызываем у этого метод move и передаем ссылку на себя(текущий объект Tractor)
     */
    public void moveForwardsNewState() {
        stateOrient.move(this);
    }

    /**
     * при поворотах используем поле с типом интерфейса IStateOrient (состояние)
     *  вызываем у этого метод turnClockWise и передаем ссылку на себя(текущий объект Tractor)
     */
    public void turnClockwiseNewState() {
        stateOrient.turnClockWise(this);
    }

    public void moveNew(String command) {
        if (command == "F")
            moveForwardsNewState();

        if (command == "T")
            turnClockwiseNewState();

    }




    @Deprecated
    public void moveForwardsNew() {
        Integer[] moveVector = orientation.getVectorForward();
        position = new int[]{position[0] + moveVector[0], position[1] + moveVector[1]};

        if (position[0] > field[0] || position[1] > field[1]) {
            throw new TractorInDitchException();
        }
    }

    @Deprecated
    public void turnClockwiseNew() {
        orientation = orientation.turnClockWise();
    }

    public int getPositionX() {
        return position[0];
    }

    public int getPositionY() {
        return position[1];
    }

    public Orientation getOrientation() {
        return orientation;
    }


    @Deprecated
    public void move(String command) {
        if (command == "F")
            moveForwards();

        if (command == "T")
            turnClockwise();

    }

    @Deprecated
    public void moveForwards() {
        if (orientation == Orientation.NORTH) {
            position = new int[]{position[0], position[1] + 1};
        } else if (orientation == Orientation.EAST) {
            position = new int[]{position[0] + 1, position[1]};
        } else if (orientation == Orientation.SOUTH) {
            position = new int[]{position[0], position[1] - 1};
        } else if (orientation == Orientation.WEST) {
            position = new int[]{position[0] - 1, position[1]};
        }
        if (position[0] > field[0] || position[1] > field[1]) {
            throw new TractorInDitchException();
        }
    }

    @Deprecated
    public void turnClockwise() {
        if (orientation == Orientation.NORTH) {
            orientation = Orientation.EAST;
        } else if (orientation == Orientation.EAST) {
            orientation = Orientation.SOUTH;
        } else if (orientation == Orientation.SOUTH) {
            orientation = Orientation.WEST;
        } else if (orientation == Orientation.WEST) {
            orientation = Orientation.NORTH;
        }
    }


}