package com.akitektuo.ejas.control;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akitektuo.ejas.R;
import com.akitektuo.ejas.util.FloorAdapter;
import com.akitektuo.ejas.util.FloorItem;

import static com.akitektuo.ejas.activitiy.MainActivity.elevator0;
import static com.akitektuo.ejas.activitiy.MainActivity.elevator1;
import static com.akitektuo.ejas.activitiy.MainActivity.elevator2;
import static com.akitektuo.ejas.activitiy.MainActivity.elevator3;
import static com.akitektuo.ejas.activitiy.MainActivity.imageArrow0;
import static com.akitektuo.ejas.activitiy.MainActivity.imageArrow1;
import static com.akitektuo.ejas.activitiy.MainActivity.imageArrow2;
import static com.akitektuo.ejas.activitiy.MainActivity.imageArrow3;
import static com.akitektuo.ejas.activitiy.MainActivity.refresh;
import static com.akitektuo.ejas.activitiy.MainActivity.textStatus0;
import static com.akitektuo.ejas.activitiy.MainActivity.textStatus1;
import static com.akitektuo.ejas.activitiy.MainActivity.textStatus2;
import static com.akitektuo.ejas.activitiy.MainActivity.textStatus3;
import static com.akitektuo.ejas.util.Constants.AVAILABLE;
import static com.akitektuo.ejas.util.Constants.BOARDING_TIME;
import static com.akitektuo.ejas.util.Constants.ELEVATOR_0;
import static com.akitektuo.ejas.util.Constants.ELEVATOR_1;
import static com.akitektuo.ejas.util.Constants.ELEVATOR_2;
import static com.akitektuo.ejas.util.Constants.ELEVATOR_3;
import static com.akitektuo.ejas.util.Constants.ELEVATOR_AT_LEVEL;
import static com.akitektuo.ejas.util.Constants.FIRST_FLOOR;
import static com.akitektuo.ejas.util.Constants.LAST_FLOOR;
import static com.akitektuo.ejas.util.Constants.NULL;
import static com.akitektuo.ejas.util.Constants.NUMBER_OF_FLOORS;
import static com.akitektuo.ejas.util.Constants.RESET_VIEW;
import static com.akitektuo.ejas.util.Constants.TRAVEL_TIME;
import static com.akitektuo.ejas.util.Constants.buttonsAvailable;

/**
 * Created by AoD Akitektuo on 22-Oct-16.
 */

public class VisualUpdater {

    private Context context;
    private ListView listView;
    private Movement movement;

    public void resetBuilding() {
        elevator0.resetElevator();
        elevator1.resetElevator();
        elevator2.resetElevator();
        elevator3.resetElevator();
        FloorItem[] floorItems = new FloorItem[NUMBER_OF_FLOORS];
        int level = LAST_FLOOR;
        for (int i = FIRST_FLOOR; i < NUMBER_OF_FLOORS; i++) {
            if (level == FIRST_FLOOR) {
                floorItems[i] = new FloorItem(buttonsAvailable, level, ELEVATOR_AT_LEVEL, ELEVATOR_AT_LEVEL, ELEVATOR_AT_LEVEL, ELEVATOR_AT_LEVEL);
            } else {
                floorItems[i] = new FloorItem(buttonsAvailable, level, NULL, NULL, NULL, NULL);
            }
            level--;
        }
        final ArrayAdapter<FloorItem> floorArrayAdapter = new FloorAdapter(getContext(), floorItems);
        getListView().setAdapter(floorArrayAdapter);
        getListView().setSelection(NUMBER_OF_FLOORS);
        updateStatusWith(textStatus0, getContext().getString(R.string.stop));
        updateStatusWith(textStatus1, getContext().getString(R.string.stop));
        updateStatusWith(textStatus2, getContext().getString(R.string.stop));
        updateStatusWith(textStatus3, getContext().getString(R.string.stop));
        arrowSetGone();
        refresh = false;
    }

    void resetStatus(Elevator elevator) {
        switch (elevator.getNumber()) {
            case ELEVATOR_0:
                imageArrow0.setVisibility(View.GONE);
                updateStatusWith(textStatus0, getContext().getString(R.string.stop));
                break;
            case ELEVATOR_1:
                imageArrow1.setVisibility(View.GONE);
                updateStatusWith(textStatus1, getContext().getString(R.string.stop));
                break;
            case ELEVATOR_2:
                imageArrow2.setVisibility(View.GONE);
                updateStatusWith(textStatus2, getContext().getString(R.string.stop));
                break;
            case ELEVATOR_3:
                imageArrow3.setVisibility(View.GONE);
                updateStatusWith(textStatus3, getContext().getString(R.string.stop));
                break;
        }
    }

    void updateForMove(final int nextFloor, final int to, final Elevator elevator, boolean up, int currentFloor) {
        switch (elevator.getNumber()) {
            case ELEVATOR_0:
                if (up) {
                    imageArrow0.setBackground(getContext().getDrawable(R.drawable.up));
                } else {
                    imageArrow0.setBackground(getContext().getDrawable(R.drawable.down));
                }
                imageArrow0.setVisibility(View.VISIBLE);
                updateStatusWith(textStatus0, getContext().getString(R.string.next_floor, currentFloor));
                break;
            case ELEVATOR_1:
                if (up) {
                    imageArrow1.setBackground(getContext().getDrawable(R.drawable.up));
                } else {
                    imageArrow1.setBackground(getContext().getDrawable(R.drawable.down));
                }
                imageArrow1.setVisibility(View.VISIBLE);
                updateStatusWith(textStatus1, getContext().getString(R.string.next_floor, currentFloor));
                break;
            case ELEVATOR_2:
                if (up) {
                    imageArrow2.setBackground(getContext().getDrawable(R.drawable.up));
                } else {
                    imageArrow2.setBackground(getContext().getDrawable(R.drawable.down));
                }
                imageArrow2.setVisibility(View.VISIBLE);
                updateStatusWith(textStatus2, getContext().getString(R.string.next_floor, currentFloor));
                break;
            case ELEVATOR_3:
                if (up) {
                    imageArrow3.setBackground(getContext().getDrawable(R.drawable.up));
                } else {
                    imageArrow3.setBackground(getContext().getDrawable(R.drawable.down));
                }
                imageArrow3.setVisibility(View.VISIBLE);
                updateStatusWith(textStatus3, getContext().getString(R.string.next_floor, currentFloor));
                break;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                elevator.setPosition(nextFloor);
                updateAdapter();
                elevator.setTime(elevator.getTime() - TRAVEL_TIME);
                getMovement().moveElevator(nextFloor, to, elevator);
            }
        }, TRAVEL_TIME);
    }

    void updateForBoard(final int nextFloor, final int to, final Elevator elevator) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                elevator.setPosition(nextFloor);
                updateAdapter();
                elevator.setTime(elevator.getTime() - BOARDING_TIME);
                getMovement().moveElevator(nextFloor, to, elevator);
            }
        }, BOARDING_TIME);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (elevator.getNumber()) {
                    case ELEVATOR_0:
                        imageArrow0.setVisibility(View.GONE);
                        updateStatusWith(textStatus0, getContext().getString(R.string.boarding));
                        break;
                    case ELEVATOR_1:
                        imageArrow1.setVisibility(View.GONE);
                        updateStatusWith(textStatus1, getContext().getString(R.string.boarding));
                        break;
                    case ELEVATOR_2:
                        imageArrow2.setVisibility(View.GONE);
                        updateStatusWith(textStatus2, getContext().getString(R.string.boarding));
                        break;
                    case ELEVATOR_3:
                        imageArrow3.setVisibility(View.GONE);
                        updateStatusWith(textStatus3, getContext().getString(R.string.boarding));
                        break;
                }
            }
        }, RESET_VIEW);
    }

    private void updateAdapter() {
        FloorItem[] floorItems = new FloorItem[NUMBER_OF_FLOORS];
        int level = NUMBER_OF_FLOORS - 1;
        int setPosition = level;
        String door0, door1, door2, door3;

        for (int i = FIRST_FLOOR; i < NUMBER_OF_FLOORS; i++) {
            door0 = isElevatorAtLevel(elevator0, level);
            door1 = isElevatorAtLevel(elevator1, level);
            door2 = isElevatorAtLevel(elevator2, level);
            door3 = isElevatorAtLevel(elevator3, level);
            floorItems[i] = new FloorItem(buttonsAvailable, level, door0, door1, door2, door3);
            level--;
        }
        ArrayAdapter<FloorItem> floorArrayAdapter = new FloorAdapter(getContext(), floorItems);
        getListView().setAdapter(floorArrayAdapter);
        getListView().setSelection(setPosition);
    }

    private String isElevatorAtLevel(Elevator elevator, int level) {
        if (elevator.getPosition() == level) {
            return ELEVATOR_AT_LEVEL;
        }
        return NULL;
    }

    public VisualUpdater(Context context, ListView listView, Movement movement) {
        setContext(context);
        setListView(listView);
        setMovement(movement);
    }

    public void updateStatusWith(TextView textView, String status) {
        textView.setText(status);
    }

    public ListView getListView() {
        return listView;
    }

    private void setListView(ListView listView) {
        this.listView = listView;
    }

    public Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    private void arrowSetGone() {
        imageArrow0.setVisibility(View.GONE);
        imageArrow1.setVisibility(View.GONE);
        imageArrow2.setVisibility(View.GONE);
        imageArrow3.setVisibility(View.GONE);
    }
}
