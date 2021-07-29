package com.interview.elevatorsystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner reader = new Scanner(System.in);

        System.out.println("Enter no of elevator");
        int noOfElevator = Integer.parseInt(reader.nextLine());
        System.out.println("Enter no of Floor in building");
        int floor = Integer.parseInt(reader.nextLine());
        System.out.println("Enter building population");
        int population = Integer.parseInt(reader.nextLine());

        if (floor > noOfElevator) {
            Building building = new Building(floor, noOfElevator, population);
            building.setAlgorithm(1);
            building.getController().setAlgorithm(1);
            System.out.println("\nAll elevators start on the central floor of the building with direction up.\n");
            building.createFloors(); // create N number of floor
            building.createElevators(); // create L number of elevators
            new Thread(building.getController()).start();
            while (true) {

                // Generate a passenger on one of the floors
                building.generatePassenger(building.getNoOfFloor());
                Thread.sleep(2000);
            }
        } else {
            System.out.println("The number of floors can not be less than the number of elevator");
        }
    }
}
