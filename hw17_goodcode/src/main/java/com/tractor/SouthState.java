package com.tractor;

/**
 * Конкретная реализация состояния "Ориентация на юг"
 */
public class SouthState extends AbstractStateOrient {
    @Override
    public void move(Tractor context) {
        int[] position = context.getPosition();
        int[] field = context.getField();
        context.setPosition(new int[]{position[0], position[1]-1});

        validatePosition(context.getPosition(),field);
    }

    @Override
    public void turnClockWise(Tractor context) {
        context.orientation=Orientation.WEST;
        context.setStateOrient(new WestState());
    }
}
