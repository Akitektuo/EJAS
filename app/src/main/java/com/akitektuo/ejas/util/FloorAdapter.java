package com.akitektuo.ejas.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akitektuo.ejas.R;
import com.akitektuo.ejas.control.Elevator;

import static com.akitektuo.ejas.activitiy.MainActivity.elevator0;
import static com.akitektuo.ejas.activitiy.MainActivity.elevator1;
import static com.akitektuo.ejas.activitiy.MainActivity.elevator2;
import static com.akitektuo.ejas.activitiy.MainActivity.elevator3;
import static com.akitektuo.ejas.activitiy.MainActivity.movement;
import static com.akitektuo.ejas.util.Constants.AVAILABLE;
import static com.akitektuo.ejas.util.Constants.ELEVATOR_AT_LEVEL;
import static com.akitektuo.ejas.util.Constants.NULL_INT;
import static com.akitektuo.ejas.util.Constants.TRAVEL_TIME;

/**
 * Created by AoD Akitektuo on 18-Oct-16.
 */

public class FloorAdapter extends ArrayAdapter<FloorItem> {

    private Context context;

    private FloorItem[] items;

    private int selectedFloor = NULL_INT;

    public FloorAdapter(Context context, FloorItem[] objects) {
        super(context, R.layout.item_level, objects);
        this.context = context;
        items = objects;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_level, parent, false);

        final FloorItem item = items[position];
        ImageView imageDoor0 = (ImageView) view.findViewById(R.id.image_elevator_door_0);
        ImageView imageDoor1 = (ImageView) view.findViewById(R.id.image_elevator_door_1);
        ImageView imageDoor2 = (ImageView) view.findViewById(R.id.image_elevator_door_2);
        ImageView imageDoor3 = (ImageView) view.findViewById(R.id.image_elevator_door_3);
        Button buttonSelectFloor = (Button) view.findViewById(R.id.button_select_floor);

        buttonSelectFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getButton().equals(AVAILABLE)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final View viewDialog = LayoutInflater.from(context).inflate(R.layout.dialog_selection, null);
                    final TextView statusSelection = (TextView) viewDialog.findViewById(R.id.text_floor);
                    statusSelection.setText(getContext().getString(R.string.floor_number, item.getLevel()));
                    builder.setView(viewDialog);
                    builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (selectedFloor != NULL_INT) {
                                callNearestElevator(item.getLevel(), selectedFloor);
                            } else {
                                Toast.makeText(context, "Select a floor!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNeutralButton("Cancel", null);
                    builder.show();
                    viewDialog.findViewById(R.id.button_0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 0;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 1;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 2;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 3;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 4;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 5;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_6).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 6;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_7).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 7;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_8).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 8;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_9).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 9;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                    viewDialog.findViewById(R.id.button_10).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedFloor = 10;
                            updateStatus(statusSelection, selectedFloor, item);
                        }
                    });
                }
            }
        });

        if (item != null) {
            if (item.getDoor0().equals(ELEVATOR_AT_LEVEL)) {
                imageDoor0.setBackground(getContext().getDrawable(R.drawable.elevator));
            }
            if (item.getDoor1().equals(ELEVATOR_AT_LEVEL)) {
                imageDoor1.setBackground(getContext().getDrawable(R.drawable.elevator));
            }
            if (item.getDoor2().equals(ELEVATOR_AT_LEVEL)) {
                imageDoor2.setBackground(getContext().getDrawable(R.drawable.elevator));
            }
            if (item.getDoor3().equals(ELEVATOR_AT_LEVEL)) {
                imageDoor3.setBackground(getContext().getDrawable(R.drawable.elevator));
            }
            if (item.getButton().equals(AVAILABLE)) {
                buttonSelectFloor.setBackground(getContext().getDrawable(R.color.colorAccent));
            }
        }
        buttonSelectFloor.setText(getContext().getString(R.string.select_floor, item.getLevel()));
        return view;
    }

    private void updateStatus(TextView textView, int whichFloor, FloorItem item) {
        textView.setText(getContext().getString(R.string.floor_number, item.getLevel())
                + "\nFloor " + whichFloor + " selected");
    }

    private void callNearestElevator(int from, int to) {
        Toast.makeText(context, "This elevator will arrive in " + convertTimeToString(3 * minTime(from, to)), Toast.LENGTH_LONG).show();
        movement.callElevator(from, to, minTimeDestination(from));
    }

    private String convertTimeToString(long time) {
        time = time / 1000;
        int min = 0;
        while (time > 59) {
            min++;
            time -= 60;
        }
        return min + " min " + time + "sec.";
    }

    private Elevator minTimeDestination(int from) {
        long elevator0Time = elevator0.getTime() + Math.abs(elevator0.getPosition() - from) * TRAVEL_TIME;
        long elevator1Time = elevator1.getTime() + Math.abs(elevator1.getPosition() - from) * TRAVEL_TIME;
        long elevator2Time = elevator2.getTime() + Math.abs(elevator2.getPosition() - from) * TRAVEL_TIME;
        long elevator3Time = elevator3.getTime() + Math.abs(elevator0.getPosition() - from) * TRAVEL_TIME;
        long time = Math.min(Math.min(elevator0Time, elevator1Time), Math.min(elevator2Time, elevator3Time));
        if (time == elevator0Time) {
            return elevator0;
        }
        if (time == elevator1Time) {
            return elevator1;
        }
        if (time == elevator2Time) {
            return elevator2;
        }
        if (time == elevator3Time) {
            return elevator3;
        }
        return null;
    }

    private long minTime(int from, int to) {
        long elevator0Time = elevator0.getTime() + Math.abs(elevator0.getPosition() - from) * TRAVEL_TIME;
        long elevator1Time = elevator1.getTime() + Math.abs(elevator1.getPosition() - from) * TRAVEL_TIME;
        long elevator2Time = elevator2.getTime() + Math.abs(elevator2.getPosition() - from) * TRAVEL_TIME;
        long elevator3Time = elevator3.getTime() + Math.abs(elevator0.getPosition() - from) * TRAVEL_TIME;
        long time = Math.min(Math.min(elevator0Time, elevator1Time), Math.min(elevator2Time, elevator3Time));
        time += Math.abs((to - from - 1) * 1000) + 5000;
        return time;
    }
}
