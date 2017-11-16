package com.tractor;

/**
 * Абстрактное состояние с общим для всех состояний методом
 */
public abstract class AbstractStateOrient implements IStateOrient {
    protected void validatePosition(int[] position, int[] field) {
        if (position[0] > field[0] || position[1] > field[1]) {
            throw new TractorInDitchException();
        }
    }
}
