package com.interview.elevatorsystem;

public class Passenger {
    private Request floorRequest;
    private Request elevatorRequest;
    private String ID;

    public Passenger(Request floorRequest, Request elevatorRequest, String ID) {
        this.floorRequest = floorRequest;
        this.elevatorRequest = elevatorRequest;
        this.ID = ID;
    }


    public Request getFloorRequest() {
        return floorRequest;
    }

    public Request getElevatorRequest() {
        return elevatorRequest;
    }

    public String getID() {
        return ID;
    }
}
