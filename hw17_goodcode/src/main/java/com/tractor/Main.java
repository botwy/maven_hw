package com.tractor;

public class Main {

    public static void main(String... args) {
        Tractor tractor = new Tractor();
        tractor.moveForwardsNew();
        System.out.println(tractor.position[0]+" "+tractor.position[1]);

        tractor.turnClockwiseNew();
        tractor.moveForwardsNew();
        System.out.println(tractor.position[0]+" "+tractor.position[1]);
        System.out.println(tractor.orientation);

        tractor.turnClockwiseNew();
        tractor.moveForwardsNew();
        System.out.println(tractor.position[0]+" "+tractor.position[1]);
        System.out.println(tractor.orientation);

        tractor.turnClockwiseNew();
        tractor.moveForwardsNew();
        System.out.println(tractor.position[0]+" "+tractor.position[1]);
        System.out.println(tractor.orientation);

        tractor.turnClockwiseNew();
        tractor.moveForwardsNew();
        System.out.println(tractor.position[0]+" "+tractor.position[1]);
        System.out.println(tractor.orientation);

        tractor.turnClockwiseNew();
        tractor.moveForwardsNew();
        System.out.println(tractor.position[0]+" "+tractor.position[1]);
        System.out.println(tractor.orientation);

    }
}
