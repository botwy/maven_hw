package com.tractor;

public class Tractor {

    int[] position = new int[] { 0, 0 };
    int[] field = new int[] { 5, 5 };
    Orientation orientation = Orientation.NORTH;

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
            position = new int[] { position[0], position[1] + 1 };
        } else if (orientation == Orientation.EAST) {
            position = new int[] { position[0] + 1, position[1] };
        } else if (orientation == Orientation.SOUTH) {
            position = new int[] { position[0], position[1] - 1 };
        } else if (orientation == Orientation.WEST) {
            position = new int[] { position[0] - 1, position[1] };
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

    public void moveNew(String command) {
        if (command == "F")
            moveForwardsNew();

        if (command == "T")
            turnClockwiseNew();

    }

    public void moveForwardsNew() {
       Integer[] moveVector = orientation.getVectorForward();
       position=new int[] {position[0]+moveVector[0],position[1]+moveVector[1]};

        if (position[0] > field[0] || position[1] > field[1]) {
            throw new TractorInDitchException();
        }
    }

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

    public static class Speed {
        public Speed(){

        }

        public static void increese(){

        }
    }

}