package com.interview.elevatorsystem;

public class Request {
    private Type type;
    private int passage;
    private int floor;
    private Direction direction;
    private String ID;

    public Request(Type type,  int floor, Direction direction,String ID) {
        this.type = type;

        this.floor = floor;
        this.direction = direction;
        this.ID = ID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPassage() {
        return passage;
    }

    public void setPassage(int passage) {
        this.passage = passage;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getID() {
        return ID;
    }
}
