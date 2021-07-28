package com.interview.elevatorsystem;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class Elevator {
    private int ID;
    private List<Request> floorRequests;
    private List<Request> elevatorRequests;
    private PriorityBlockingQueue<Request> sequence;
    private int currentFloor;
    private Direction direction;
    private boolean idle = true;
    private int capacity;
    private int interFloorHeight;
    private int noOfFloor;
    private int noOfElevator;
    private int algorithm;
    private int passengerLoadTime;
    private int passengerUnLoadTime;
    private int velocity;
    private boolean DEBUG = true;
    private Comparator<Request> comparator;

    public Elevator(int ID, int algorithm, int passengerLoadTime, int passengerUnLoadTime,
                    int velocity, int capacity, int interFloorHeight) {
        this.ID = ID;
        this.algorithm = algorithm;
        this.passengerLoadTime = passengerLoadTime;
        this.passengerUnLoadTime = passengerUnLoadTime;
        this.velocity = velocity;
        this.capacity = capacity;
        this.interFloorHeight = interFloorHeight;

        this.floorRequests = new CopyOnWriteArrayList<>();
        this.elevatorRequests = new CopyOnWriteArrayList<>();

        this.comparator = new RequestComparator();
        this.sequence = new PriorityBlockingQueue<>(100, this.comparator);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Request> getFloorRequests() {
        return floorRequests;
    }

    public void setFloorRequests(List<Request> floorRequest) {
        this.floorRequests = floorRequests;
    }

    public List<Request> getElevatorRequests() {
        return elevatorRequests;
    }

    public void setElevatorRequests(List<Request> elevatorRequests) {
        this.elevatorRequests = elevatorRequests;
    }

    public PriorityBlockingQueue<Request> getSequence() {
        return sequence;
    }

    public void setSequence(PriorityBlockingQueue<Request> sequence) {
        this.sequence = sequence;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getInterFloorHeight() {
        return interFloorHeight;
    }

    public void setInterFloorHeight(int interFloorHeight) {
        this.interFloorHeight = interFloorHeight;
    }


    public int getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public int getPassengerLoadTime() {
        return passengerLoadTime;
    }

    public void setPassengerLoadTime(int passengerLoadTime) {
        this.passengerLoadTime = passengerLoadTime;
    }

    public int getPassengerUnLoadTime() {
        return passengerUnLoadTime;
    }

    public void setPassengerUnLoadTime(int passengerUnLoadTime) {
        this.passengerUnLoadTime = passengerUnLoadTime;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getNoOfFloor() {
        return noOfFloor;
    }

    public void setNoOfFloor(int noOfFloor) {
        this.noOfFloor = noOfFloor;
    }

    public int getNoOfElevator() {
        return noOfElevator;
    }

    public void setNoOfElevator(int noOfElevator) {
        this.noOfElevator = noOfElevator;
    }

    private class RequestComparator implements Comparator<Request> {

        @Override
        public int compare(Request x, Request y) {

            if (x.getPassage() == y.getPassage()) {

                if ((x.getPassage() == 1) || (x.getPassage() == 3)) {

                    if (x.getFloor() < y.getFloor()) {
                        return -1;
                    } else if (x.getFloor() > y.getFloor()) {
                        return 1;
                    }

                    return 0;

                } else if (x.getPassage() == 2) {

                    if (x.getFloor() > y.getFloor()) {
                        return -1;
                    } else if (x.getFloor() < y.getFloor()) {
                        return 1;
                    }

                    return 0;

                }

            } else if (x.getPassage() > y.getPassage()) {
                return 1;
            }

            return -1;
        }
    }

    public void performJobThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        performJob();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error in performJob thread.");
                    }
                }
            }
        }).start();
    }
    public void elevatorControllerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        elevatorController();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error in elevatorController thread.");
                    }
                }
            }
        }).start();
    }

    public void receiveJob(Passenger temp) throws InterruptedException {

        Request floorRequest = temp.getFloorRequest();
        Request elevatorRequest = temp.getElevatorRequest();

        this.floorRequests.add(floorRequest);
        this.elevatorRequests.add(elevatorRequest);

        if (DEBUG) {
            System.out.println("--------------------------");
            for (Request call : sequence) {
                System.out.printf("Call direction: %s, Call passage: %d, Call floor: %d, Request type: %s, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
            }
            System.out.println("--------------------------");
            for (Request request : floorRequests) {
                System.out.printf("Call direction: %s, Call passage: %d, Call floor: %d, Request type: %s, Call ID: %s.\n", request.getDirection(), request.getPassage(), request.getFloor(), request.getType(), request.getID());
            }
            System.out.println("--------------------------");
            for (Request request : elevatorRequests) {
                System.out.printf("Call direction: %s, Call passage: %d, Call floor: %d, Request type: %s, Call ID: %s.\n", request.getDirection(), request.getPassage(), request.getFloor(), request.getType(), request.getID());
            }
            System.out.println("--------------------------");
        }
    }

    private void elevatorController() throws InterruptedException {

        if (this.floorRequests.size() > 0) {

            Request tempCall = this.floorRequests.get(0);
            this.floorRequests.remove(0);

            // Assign passage to a newly arrived floorCall

            // Same direction and higher than currentFloor - P1
            // Opposite direction - P2
            // Same direction and lower than currentFloor - P3
            if (this.direction == Direction.DOWN) {
                if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(1);
                } else if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(3);
                } else {
                    tempCall.setPassage(2);
                }
            } else {

                // Same direction and lower than currentFloor - P1
                // Opposite direction - P2
                // Same direction and higher than currentFloor - P3
                if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(1);
                } else if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(3);
                } else {
                    tempCall.setPassage(2);
                }
            }

            this.sequence.add(tempCall);
        }
    }

    private void redefinePassage() {

        for (Request tempCall : sequence) {


            // Same direction and higher than currentFloor - P1
            // Opposite direction - P2
            // Same direction and lower than currentFloor - P3
            if (this.direction == Direction.UP) {
                if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(1);
                } else if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(3);
                } else {
                    tempCall.setPassage(2);
                }
            } else {

                // Same direction and lower than currentFloor - P1
                // Opposite direction - P2
                // Same direction and higher than currentFloor - P3
                if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(1);
                } else if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(3);
                } else {
                    tempCall.setPassage(2);
                }
            }


        }
    }

    public void performJob() throws InterruptedException {

        if (this.sequence.size() > 0) {

            // Get Call from sequence
            Request tempCall = this.sequence.take();

            if (DEBUG) {
                System.out.println("\n\n**************************");
                System.out.printf("Elevator %s, direction: %s, current floor: %d.\n", this.ID, this.direction, this.currentFloor);
                System.out.printf("Got a Job | direction: %s, passage: %d, floor: %d, type: %s, ID: %s\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());
                System.out.println("**************************\n\n");
            }

            if (tempCall.getFloor() == this.currentFloor) {
                checkSequence(tempCall);
            } else {

                // Update the direction of the elevator based
                // on the position of the current floor
                // Since the direction has changed, we must
                // reassign passage to all calls in the sequence
                if (tempCall.getFloor() < this.currentFloor) {
                    this.direction = Direction.DOWN;
                    redefinePassage();
                } else if (tempCall.getFloor() > this.currentFloor) {
                    this.direction = Direction.UP;
                    redefinePassage();
                }

                // Simulate elevator movement through the floors of the building
                while ((this.currentFloor != tempCall.getFloor()) &&
                        (this.currentFloor >= 0) &&
                        (this.currentFloor <= (this.noOfFloor - 1))) {

                    this.idle = false;

                    // Direction is up
                    if (this.direction == Direction.UP && this.currentFloor != (this.noOfFloor - 1)) {

                        this.currentFloor += 1;
                        Thread.sleep(this.velocity * this.interFloorHeight * 1000);

                        if (DEBUG) {
                            System.out.printf("\n\n+++++ Elevator %s, direction: %s, current floor: %d, target floor: %d. +++++\n", this.ID, this.direction, this.currentFloor, tempCall.getFloor());
                            System.out.printf("+++++ Call direction: %s, Call passage: %d, Call floor: %d, Request type: %s, Call ID: %s. +++++\n\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());
                        }

                        checkSequence(tempCall);

                        if (!DEBUG) {
                            displayElevator();
                        }

                    } else if (this.direction == Direction.DOWN && this.currentFloor != 0) {

                        this.currentFloor -= 1;
                        Thread.sleep(this.velocity * this.interFloorHeight * 1000);

                        if (DEBUG) {
                            System.out.printf("\n\n+++++ Elevator %s, direction:%s, current floor: %d, target floor: %d. +++++\n", this.ID, this.direction, this.currentFloor, tempCall.getFloor());
                            System.out.printf("+++++ Call direction: %s, Call passage: %d, Call floor: %d, Request type: %s, Call ID: %s. +++++\n\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());
                        }

                        checkSequence(tempCall);

                        if (!DEBUG) {
                            displayElevator();
                        }

                    } else {
                        System.out.println("\n\n\n\n! + ! + ! Elevator is out of range - performJob() ! + ! + !\n\n\n\n");
                        System.exit(0);
                    }
                }
            }

            this.idle = true;
        }
    }

    private void checkSequence(Request tempCall) throws InterruptedException {

        // Here we are looking for the elevatorRequest of the current floorCall
        if (tempCall.getType() == Type.FLOOR && tempCall.getFloor() == this.currentFloor) {

            int removeIndex = 0;
            boolean foundelevatorRequest = false;

            // Traverse carFloors array to look for a
            // elevatorRequest with the same ID as tempCall
            for (int i = 0; i < this.elevatorRequests.size(); ++i) {

                Request tempelevatorRequest = this.elevatorRequests.get(i);

                if (tempelevatorRequest.getID().equals(tempCall.getID())) {

                    removeIndex = i;

                    // Assign passage to elevatorRequest
                    // Same direction and higher than currentFloor - P1
                    // Opposite direction - P2

                    if (this.direction == Direction.UP) {
                        if ((tempelevatorRequest.getFloor() > this.currentFloor) && (tempelevatorRequest.getDirection() == this.direction)) {
                            tempelevatorRequest.setPassage(1);
                        } else {
                            tempelevatorRequest.setPassage(2);
                        }
                    } else {
                        if ((tempelevatorRequest.getFloor() < this.currentFloor) && (tempelevatorRequest.getDirection() == this.direction)) {
                            tempelevatorRequest.setPassage(1);
                        } else {
                            tempelevatorRequest.setPassage(2);
                        }
                    }

                    // Add elevatorRequest to sequence
                    this.sequence.add(tempelevatorRequest);

                    foundelevatorRequest = true;
                    break;
                }
            }

            // Remove elevatorRequest from elevatorRequests array
            if (foundelevatorRequest) {
                this.elevatorRequests.remove(removeIndex);
            }
        }

        // Check the Calls in the sequence, if the sequence is not empty
        // Here we are looking for all elevatorRequests and floorCalls that can be removed from sequence
        if (this.sequence.size() > 0) {

            // Traverse the Calls in the sequence to find out if
            // any Calls need to be remove, because their floor matches the currentFloor of the elevator
            for (Request call : sequence) {

                // Remove all elevatorRequests whose floor is the current floor of the elevator
                // The passengers whose elevatorRequest is the same as currentFloor have already arrived
                if (call.getType() == Type.ELEVATOR && call.getFloor() == this.currentFloor) {
                    this.sequence.remove(call);
                }

                // Remove all floorCalls whose floor is the current floor of the elevator,
                // and add elevatorRequests with the same ID to the sequence
                // The passengers whose floorCall is the same as currentFloor have boarded the elevator
                // and pressed a button inside the elevator (made a elevatorRequest)
                if (call.getType() == Type.FLOOR && call.getFloor() == this.currentFloor) {

                    int removeIndex = 0;
                    boolean foundelevatorRequest = false;

                    // Traverse carFloors array
                    for (int i = 0; i < this.elevatorRequests.size(); ++i) {

                        Request tempelevatorRequest = this.elevatorRequests.get(i);

                        if (tempelevatorRequest.getID().equals(call.getID())) {

                            removeIndex = i;

                            // Assign passage to elevatorRequest
                            // Same direction and higher than currentFloor - P1
                            // Opposite direction - P2

                            if (this.direction == Direction.UP) {
                                if ((tempelevatorRequest.getFloor() > this.currentFloor) && (tempelevatorRequest.getDirection() == this.direction)) {
                                    tempelevatorRequest.setPassage(1);
                                } else {
                                    tempelevatorRequest.setPassage(2);
                                }
                            } else {
                                if ((tempelevatorRequest.getFloor() < this.currentFloor) && (tempelevatorRequest.getDirection() == this.direction)) {
                                    tempelevatorRequest.setPassage(1);
                                } else {
                                    tempelevatorRequest.setPassage(2);
                                }
                            }

                            // Add elevatorRequest to sequence
                            this.sequence.add(tempelevatorRequest);
                            foundelevatorRequest = true;
                            break;
                        }
                    }

                    // Remove elevatorRequest from elevatorRequests array
                    if (foundelevatorRequest) {
                        this.elevatorRequests.remove(removeIndex);
                    }

                    // Remove the floorCall from the sequence
                    this.sequence.remove(call);
                }

            }
        }
    }

    private void displayElevator() {

        System.out.printf("\n\nElevator %d\n", this.ID);
        System.out.println("------------------------------------------\n");
        for (int i = 0; i < noOfFloor; ++i) {

            if (i == this.currentFloor) {
                System.out.print(" == ");
            } else {
                System.out.printf(" %d ", i);
            }
        }

        if (this.direction == Direction.UP) {
            System.out.println("\n\n-->");
        } else {
            System.out.println("\n\n<--");
        }
        System.out.println("------------------------------------------\n\n");

    }

}
