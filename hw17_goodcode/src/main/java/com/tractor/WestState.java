package com.tractor;

/**
 * Конкретная реализация состояния "Ориентация на запад"
 */
public class WestState extends AbstractStateOrient {
    @Override
    public void move(Tractor context) {
        int[] position = context.getPosition();
        int[] field = context.getField();
        context.setPosition(new int[]{position[0]-1, position[1]});

        validatePosition(context.getPosition(),field);

    }

    @Override
    public void turnClockWise(Tractor context) {
        context.orientation=Orientation.NORTH;
        context.setStateOrient(new NorthState());
    }
}
