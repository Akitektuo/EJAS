package com.akitektuo.ejas.util;

/**
 * Created by AoD Akitektuo on 18-Oct-16.
 */

public class FloorItem {

    private String door0;
    private String door1;
    private String door2;
    private String door3;
    private String button;
    private int level;

    public FloorItem(String button, int level, String door0, String door1, String door2, String door3) {
        setButton(button);
        setLevel(level);
        setDoor0(door0);
        setDoor1(door1);
        setDoor2(door2);
        setDoor3(door3);
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getDoor3() {
        return door3;
    }

    public void setDoor3(String door3) {
        this.door3 = door3;
    }

    public String getDoor2() {
        return door2;
    }

    public void setDoor2(String door2) {
        this.door2 = door2;
    }

    public String getDoor1() {
        return door1;
    }

    public void setDoor1(String door1) {
        this.door1 = door1;
    }

    public String getDoor0() {
        return door0;
    }

    public void setDoor0(String door0) {
        this.door0 = door0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
