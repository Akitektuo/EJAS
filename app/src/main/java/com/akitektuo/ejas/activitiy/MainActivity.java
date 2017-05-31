package com.akitektuo.ejas.activitiy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akitektuo.ejas.R;
import com.akitektuo.ejas.control.Elevator;
import com.akitektuo.ejas.control.Movement;
import com.akitektuo.ejas.control.VisualUpdater;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.akitektuo.ejas.control.Movement.soundStop;
import static com.akitektuo.ejas.control.Movement.soundStopId;
import static com.akitektuo.ejas.util.Constants.AVAILABLE;
import static com.akitektuo.ejas.util.Constants.BOARDING_TIME;
import static com.akitektuo.ejas.util.Constants.FIRST_FLOOR;
import static com.akitektuo.ejas.util.Constants.NULL;
import static com.akitektuo.ejas.util.Constants.buttonsAvailable;
import static com.akitektuo.ejas.util.Constants.runnables;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button buttonAutomatic;
    private Button buttonManual;
    private boolean settings = false;
    private RelativeLayout layoutManual;
    private RelativeLayout layoutControl;
    public static Button buttonRefresh;
    private ListView listLevels;
    public static boolean refresh = false;
    public static TextView textStatus0;
    public static TextView textStatus1;
    public static TextView textStatus2;
    public static TextView textStatus3;
    public static ImageView imageArrow0;
    public static ImageView imageArrow1;
    public static ImageView imageArrow2;
    public static ImageView imageArrow3;
    public static VisualUpdater visualUpdater;
    public static Elevator elevator0;
    public static Elevator elevator1;
    public static Elevator elevator2;
    public static Elevator elevator3;
    public static Movement movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAutomatic = (Button) findViewById(R.id.button_automatic);
        buttonManual = (Button) findViewById(R.id.button_manual);
        buttonRefresh = (Button) findViewById(R.id.button_refresh);
        textStatus0 = (TextView) findViewById(R.id.text_status_auto_0);
        textStatus1 = (TextView) findViewById(R.id.text_status_auto_1);
        textStatus2 = (TextView) findViewById(R.id.text_status_auto_2);
        textStatus3 = (TextView) findViewById(R.id.text_status_auto_3);
        imageArrow0 = (ImageView) findViewById(R.id.image_arrow_0);
        imageArrow1 = (ImageView) findViewById(R.id.image_arrow_1);
        imageArrow2 = (ImageView) findViewById(R.id.image_arrow_2);
        imageArrow3 = (ImageView) findViewById(R.id.image_arrow_3);
        layoutControl = (RelativeLayout) findViewById(R.id.layout_control);
        listLevels = (ListView) findViewById(R.id.list_levels);
        buttonAutomatic.setOnClickListener(this);
        buttonManual.setOnClickListener(this);
        buttonRefresh.setOnClickListener(this);
        movement = new Movement();
        soundStop = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        soundStopId = soundStop.load(this, R.raw.elevator_sound, 1);
        elevator0 = new Elevator(0);
        elevator1 = new Elevator(1);
        elevator2 = new Elevator(2);
        elevator3 = new Elevator(3);
        runnables = new ArrayList<>();
        visualUpdater = new VisualUpdater(this, listLevels, movement);
        visualUpdater.resetBuilding();
        Toast.makeText(this, "Pressing the stop button will restart the app.\nThe text might not feet on your screen.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_automatic:
                switchToAuto();
                break;
            case R.id.button_manual:
                switchToManual();
                System.out.println(elevator0.getPosition() + "            " + elevator1.getPosition() + "            " +
                        elevator2.getPosition() + "            " + elevator3.getPosition());
                break;
            case R.id.button_refresh:
                if (refresh) {
                    doRestart(this);
                } else {
                    buttonRefresh.setBackground(getDrawable(R.drawable.replay));
                    callNearestElevator(10, 3);
                    callNearestElevator(2, 5);
                    callNearestElevator(4, 5);
                    callNearestElevator(1, 9);
                    callNearestElevator(5, 2);
                    callNearestElevator(10, 7);
                    callNearestElevator(10, 0);
                    callNearestElevator(3, 8);
                    specifyFinish();
                    refresh = true;
                }
                break;
        }
    }

    private void switchToAuto() {
        if (settings) {
            buttonsAvailable = NULL;
            visualUpdater.resetBuilding();
            buttonAutomatic.setBackground(getDrawable(R.color.colorAccent));
            buttonManual.setBackground(getDrawable(R.color.colorPrimary));
            buttonRefresh.setBackground(getDrawable(R.drawable.play));
            refresh = false;
            settings = false;
        }
    }

    private void switchToManual() {
        if (!settings && !refresh) {
            buttonsAvailable = AVAILABLE;
            visualUpdater.resetBuilding();
            buttonAutomatic.setBackground(getDrawable(R.color.colorPrimary));
            buttonManual.setBackground(getDrawable(R.color.colorAccent));
            buttonRefresh.setBackground(getDrawable(R.drawable.replay));
            refresh = true;
            settings = true;
        }
    }

    public static void doRestart(Context c) {
        if (c != null) {
            PackageManager pm = c.getPackageManager();
            if (pm != null) {
                Intent mStartActivity = pm.getLaunchIntentForPackage(
                        c.getPackageName()
                );
                if (mStartActivity != null) {
                    mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    int mPendingIntentId = 223344;
                    PendingIntent mPendingIntent = PendingIntent
                            .getActivity(c, mPendingIntentId, mStartActivity,
                                    PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    System.exit(0);
                }
            }
        }
    }

    public void specifyFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonRefresh.setBackground(getDrawable(R.drawable.play));
                visualUpdater.resetBuilding();
            }
        }, getLongestTime() + BOARDING_TIME);
        Log.d(TAG, "The button will change in " + elevator0.getTime() + "milliseconds.");
    }

    private long getLongestTime() {
        return Math.max(Math.max(elevator0.getTime(), elevator1.getTime()), Math.max(elevator2.getTime(), elevator3.getTime()));
    }

    private void callNearestElevator(int from, int to) {
        if (minTime() == elevator0.getTime()) {
            movement.callElevator(from, to, elevator0);
        } else if (minTime() == elevator1.getTime()) {
            movement.callElevator(from, to, elevator1);
        } else if (minTime() == elevator2.getTime()) {
            movement.callElevator(from, to, elevator2);
        } else if (minTime() == elevator3.getTime()) {
            movement.callElevator(from, to, elevator3);
        }
    }

    private long minTime() {
        return Math.min(Math.min(elevator0.getTime(), elevator1.getTime()), Math.min(elevator2.getTime(), elevator3.getTime()));
    }
}
