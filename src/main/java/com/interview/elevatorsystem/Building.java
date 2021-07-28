package com.interview.elevatorsystem;

import java.util.Random;
import java.util.Scanner;

public class Building {
    private int noOfFloor; // Number of floors
    private int noOfElevator; // Number of elevators
    private int buildingPopulation; // Building population
    private int algorithm; // Desired algorithm will be passed as a CL arg

    private Elevator elevatorGroup[]; // An array of L elevators
    private Floor floors[]; // An array of N floors
    private GroupElevatorController controller; // reference to the controller used for controller setup

    private Random rand;

    public Building(int noOfFloor, int noOfElevator, int buildingPopulation) {
      this.noOfFloor = noOfFloor;
      this.noOfElevator = noOfElevator;
      this.buildingPopulation = buildingPopulation;

        this.elevatorGroup = new Elevator[this.noOfElevator];
        this.floors = new Floor[this.noOfFloor];

        this.controller = new GroupElevatorController(this.elevatorGroup, this.floors);
        this.controller.setNoOfFloor(this.noOfFloor);
        this.controller.setNoOfElevators(this.noOfElevator);
        this.controller.setPopulation(this.buildingPopulation);

        this.rand = new Random();
    }
    public int getNoOfFloor() {
        return noOfFloor;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    private void createElevators() {
        for (int i = 0; i < this.noOfElevator; ++i) {
            this.elevatorGroup[i] = new Elevator(i, this.algorithm, 1,
                    1, 1, this.buildingPopulation / 4, 3);
            this.elevatorGroup[i].setCurrentFloor(noOfFloor / 2);
            this.elevatorGroup[i].setDirection(Direction.UP);
            this.elevatorGroup[i].setNoOfFloor(noOfFloor);
            this.elevatorGroup[i].setNoOfElevator(noOfElevator);
        }

        // Create elevator threads
        for (int i = 0; i < this.noOfElevator; ++i) {
            this.elevatorGroup[i].elevatorControllerThread();

            this.elevatorGroup[i].performJobThread();


        }
    }

    private void createFloors() {
        for (int i = 0; i < this.noOfFloor; ++i) {
            this.floors[i] = new Floor(i);
        }
    }

    public GroupElevatorController getController() {
        return controller;
    }

    /**
     * Randomly selects a floor from the floors array and
     * calls the generatePassenger method on the Floor(randFloor) object.
     */
    private void generatePassenger(int N) throws InterruptedException {
        int randFloor = this.rand.nextInt(N);
        floors[randFloor].generatePassenger(N);
    }

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
                Thread.sleep(20000);
            }
        } else {
            System.out.println("The number of floors can not be less than the number of elevator");
        }
    }
}
