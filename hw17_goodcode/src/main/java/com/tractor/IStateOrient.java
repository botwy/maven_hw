package com.tractor;

/**
 * состояние, которое определяет для трактора движение вперед и повороты против часовой стрелки
 */
public interface IStateOrient {
    void move(Tractor context);
    void turnClockWise(Tractor context);
}
