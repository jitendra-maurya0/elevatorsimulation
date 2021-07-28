package com.interview.elevatorsystem;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class Floor {
    private LinkedBlockingQueue<Passenger> passengers;
    private int ID;
    private boolean DEBUG = true;
    public Floor(int ID){
        this.ID =ID;
        passengers = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<Passenger> getPassengers() {
        return passengers;
    }
    public void generatePassenger(int numberOfFloors) throws InterruptedException {

        Random rand = new Random();

        String ID = UUID.randomUUID().toString(); // Create passenger ID

        int randFloor = this.ID;

        // If randFloor is the top floor, then the direction is always down
        // Else if randFloor is the main floor, then the direction is always up
        // Else the direction is chosen randomly
        Direction direction = Direction.HOLD;
        if(randFloor == 0) {
            direction = Direction.UP; // Direction is up
        }else if(randFloor == (numberOfFloors - 1)){
            direction = Direction.DOWN; // Direction is down
        }

        if (DEBUG) {
            System.out.println("direction : "+direction);
            System.out.println("new request from floor : "+randFloor);
        }

        Request floorRequest = new Request(Type.FLOOR, randFloor, direction, ID);

        // Randomly generate an exitCall, based on randFloor
        int exitFloor = 0;
        if(direction == Direction.UP) {

            // Generate random number, until it's greater than randFloor
            exitFloor = rand.nextInt(numberOfFloors);
            while (exitFloor <= randFloor){
                exitFloor = rand.nextInt(numberOfFloors);
            }
        }else{

            // Generate random number, until it's smaller than randFloor
            exitFloor = rand.nextInt(numberOfFloors);
            while (exitFloor >= randFloor){
                exitFloor = rand.nextInt(numberOfFloors);
            }
        }

        if (DEBUG) {
            System.out.println("opening door on floor : "+exitFloor);
        }

        Request elevatorRequest  = new Request(Type.ELEVATOR, exitFloor, direction, ID);

        this.passengers.put(new Passenger(floorRequest, elevatorRequest, ID)); // Create a Passenger object and add it the to the passengers array
    }
}
