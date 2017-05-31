package com.akitektuo.ejas.control;

/**
 * Created by AoD Akitektuo on 22-Oct-16.
 */

public class Elevator {

    private int position;

    private int positionCalculation;

    private long time;

    private int number;

    public Elevator(int number) {
        setPosition(0);
        setPositionCalculation(0);
        setTime(0);
        setNumber(number);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getTime() {
        return time;
    }

    public void resetElevator() {
        setPosition(0);
        setPositionCalculation(0);
        setTime(0);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPositionCalculation() {
        return positionCalculation;
    }

    public void setPositionCalculation(int positionCalculation) {
        this.positionCalculation = positionCalculation;
    }
}
