package com.interview.elevatorsystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FloorTest {

    private Floor floor;

    @Before
    public void setup() {
        floor = new Floor(1);
    }

    @Test
    public void getPassengers() throws InterruptedException {

        //Given
        String ID = UUID.randomUUID().toString();
        Request floorRequest = new Request(Type.FLOOR, 0, Direction.UP, ID);
        Request elevatorRequest = new Request(Type.ELEVATOR, 2, Direction.UP, ID);
        Passenger passenger = new Passenger(floorRequest, elevatorRequest, ID);
        LinkedBlockingQueue<Passenger> passengers = new LinkedBlockingQueue<>();
        passengers.add(passenger);

        //when
        floor.generatePassenger(10);
        //then
        assertEquals(passengers.size(), floor.getPassengers().size());


    }


}