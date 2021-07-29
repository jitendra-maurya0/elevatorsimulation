package com.interview.elevatorsystem;

import java.util.Random;
import java.util.Scanner;

public class Building {
    private int noOfFloor; // Number of floors
    private int noOfElevator; // Number of elevators
    private int buildingPopulation; // Building population
    private int algorithm;

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

    public void createElevators() {
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

    public void createFloors() {
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
    public void generatePassenger(int noOfFloor) throws InterruptedException {
        int randFloor = this.rand.nextInt(noOfFloor);
        floors[randFloor].generatePassenger(noOfFloor);
    }


}
