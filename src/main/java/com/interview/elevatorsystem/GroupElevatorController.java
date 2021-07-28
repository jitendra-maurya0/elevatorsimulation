package com.interview.elevatorsystem;

public class GroupElevatorController implements Runnable {

    private Elevator elevatorGroup[];
    private Floor floors[];

    private int algorithm;
    private int start;

    private int noOfFloor; // Number of floors
    private int noOfElevators; // Number of elevators
    private int population; // Building population

    private RoundRobin roundRobin;


    public GroupElevatorController(Elevator[] elevatorGroup, Floor[] floors) {

        super();

        this.elevatorGroup = elevatorGroup;
        this.floors = floors;

        this.start = 0;

        this.roundRobin = new RoundRobin();

    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }



    @Override
    public void run() {

        try {
            while (true) {
                scheduler();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("GroupElevatorController thread failed!");
        }
    }

    /**
     * Scan the floors array, looks for a floor with at least one passenger.
     * Based on the algorithm, assigns a Passenger to one of the elevators
     * from the elevatorGroup array.
     */
    private void scheduler() throws InterruptedException {

        int chosenElevator = 0;
        boolean foundPassenger = false;

        Passenger tempPassenger = null; // Create a dummy Passenger object

        // Look for a floor with at least one passenger
        for (int i = this.start; i < floors.length; ++i) {

            Floor floor = floors[i];

            if (floor.getPassengers().size() > 0) {

                tempPassenger = floor.getPassengers().take(); // Remove the passenger from queue

                // Remembers from which index to start scanning next time
                if (i == (floors.length - 1)) {
                    this.start = 0;
                } else {
                    this.start = i + 1;
                }

                foundPassenger = true;
                break;
            }

            // Remembers from which index to start scanning next time
            // even though no passenger was found
            if (i == (floors.length - 1)) {
                this.start = 0;
            } else {
                this.start = i + 1;
            }
        }

        if (foundPassenger) {


            chosenElevator = roundRobin.choseElevator(elevatorGroup, this.noOfElevators);


            this.elevatorGroup[chosenElevator].receiveJob(tempPassenger); // Assign a passenger to an elevator
        }

    }

    public int getNoOfFloor() {
        return noOfFloor;
    }

    public void setNoOfFloor(int noOfFloor) {
        this.noOfFloor = noOfFloor;
    }

    public int getNoOfElevators() {
        return noOfElevators;
    }

    public void setNoOfElevators(int noOfElevators) {
        this.noOfElevators = noOfElevators;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}