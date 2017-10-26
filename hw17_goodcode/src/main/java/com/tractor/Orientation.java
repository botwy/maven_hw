package com.tractor;

public enum Orientation {

     NORTH {public Integer[] getVectorForward(){return new Integer[]{0,1};}}
    , WEST {public Integer[] getVectorForward(){return new Integer[]{-1,0};}}
    , SOUTH {public Integer[] getVectorForward(){return new Integer[]{0,-1};}}
    , EAST {public Integer[] getVectorForward(){return new Integer[]{1,0};}}
    ;

    public abstract Integer[] getVectorForward();

    public Orientation turnClockWise() {
        Orientation[] arrOrient = Orientation.values();
        for (int i = 1; i <arrOrient.length ; i++)
            if (arrOrient[i].equals(this))
                return arrOrient[i-1];

                return arrOrient[arrOrient.length-1];
    }
}