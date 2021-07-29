package com.interview.elevatorsystem;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class BuildingTest {

    private Building building;

    @Before
    public void setup() {
        building = new Building(20, 4, 10);
    }

    @Test
    public void getNoOfFloor() {
        // given
        int noOfFloor = 20;
        //then
        assertEquals(noOfFloor, building.getNoOfFloor());
    }

    @Test
    public void createElevators() {
    }

    @Test
    public void createFloors() {
    }

    @Test
    public void getController() {
        assertNotNull(building.getController());
    }



    @Test
    public void generatePassenger() throws InterruptedException {
        //given
        building.createFloors();
        //when
        building.generatePassenger(10);
    }
}
