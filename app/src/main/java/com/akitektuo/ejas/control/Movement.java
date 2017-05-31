package com.akitektuo.ejas.control;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

import com.akitektuo.ejas.R;

import static com.akitektuo.ejas.activitiy.MainActivity.visualUpdater;
import static com.akitektuo.ejas.util.Constants.BOARDING_TIME;
import static com.akitektuo.ejas.util.Constants.RESET_VIEW;
import static com.akitektuo.ejas.util.Constants.TRAVEL_TIME;

/**
 * Created by AoD Akitektuo on 22-Oct-16.
 */


public class Movement {

    public static SoundPool soundStop;

    public static int soundStopId;

    void moveElevator(int from, int to, Elevator elevator) {
        if (isElevatorThere(from, to)) {
            soundStop.play(soundStopId, 1, 1, 1, 0, 1);
            visualUpdater.resetStatus(elevator);
            return;
        }
//        elevator.setPosition(getNextFloor(from, to));
        boolean up = false;
        if (getNextFloor(from, to) > from) {
            up = true;
        }
        visualUpdater.updateForMove(getNextFloor(from, to), to, elevator, up, from);
    }

    private void boardElevator(int from, int to, Elevator elevator) {
        if (isElevatorThere(from, to)) {
            soundStop.play(soundStopId, 1, 1, 1, 0, 0.50f);
            visualUpdater.resetStatus(elevator);
            return;
        }
//        elevator.setPosition(getNextFloor(from, to));
        visualUpdater.updateForBoard(getNextFloor(from, to), to, elevator);
    }

    private int getNextFloor(int from, int to) {
        if (from < to) {
            from++;
        } else {
            from--;
        }
        return from;
    }

    public void callElevator(final int from, final int to, final Elevator elevator) {
        final int position = elevator.getPositionCalculation();
        long timeCall;
        if (from > position) {
            timeCall = (from - position) * TRAVEL_TIME;
        } else {
            timeCall = (position - from) * TRAVEL_TIME;
        }
        elevator.setPositionCalculation(to);
        final long finalTimeCall = timeCall;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveElevator(position, from, elevator);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boardElevator(from, to, elevator);
                    }
                }, finalTimeCall + RESET_VIEW);
            }
        }, elevator.getTime());
        if (from > to) {
            elevator.setTime(elevator.getTime() + timeCall + (from - to) * TRAVEL_TIME + BOARDING_TIME);
        } else {
            elevator.setTime(elevator.getTime() + timeCall + (to - from) * TRAVEL_TIME + BOARDING_TIME);
        }
    }

    private boolean isElevatorThere(int from, int to) {
        return from == to;
    }
}