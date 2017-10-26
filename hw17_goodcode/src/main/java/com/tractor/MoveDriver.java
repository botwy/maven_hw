package com.tractor;

import java.util.EnumMap;

public class MoveDriver {
    private EnumMap<Orientation,Integer[]> vector_map;

    public MoveDriver() {
        this.vector_map.put(Orientation.NORTH, new Integer[]{0,1});
        this.vector_map.put(Orientation.EAST, new Integer[]{1,0});
        this.vector_map.put(Orientation.SOUTH, new Integer[]{0,-1});
        this.vector_map.put(Orientation.WEST, new Integer[]{-1,0});
    }

    public Integer[] getVectorForward(Orientation direction) {
        return vector_map.get(direction);
    }


}
