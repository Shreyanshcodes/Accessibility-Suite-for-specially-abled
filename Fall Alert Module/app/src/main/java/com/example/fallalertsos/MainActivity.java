package com.example.fallalertsos;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.tomer.fadingtextview.FadingTextView;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button locationbtn,contactSOSbtn,sosbtn,contactSavebtn;
    FusedLocationProviderClient fusedLocationProviderClient;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private FadingTextView fadingTextView;
    float axisX, axisY, axisZ;
    float Fall;
    boolean flag = false, flagfall = false, flaghit = false;
    public int i = 1, k = 0, p = 0;
    public static LinkedList<String> q = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationbtn = findViewById(R.id.locbtn);
        contactSOSbtn = findViewById(R.id.contactSOSBtn);
        fadingTextView = findViewById(R.id.fading_text_view);
        sosbtn = findViewById(R.id.sos_btn);
        contactSavebtn = findViewById(R.id.soscontact_btn);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mAccelerometer != null) {
            mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL); //boolean
        }

        contactSOSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAlarm = new Intent(MainActivity.this, Alarm.class);
                startActivity(intentAlarm);
            }
        });
        contactSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContactSave = new Intent(MainActivity.this, RegisterContacts.class);
                startActivity(intentContactSave);
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        axisX = sensorEvent.values[0];
        axisY = sensorEvent.values[1];
        axisZ = sensorEvent.values[2];

        Fall = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);
        Fall = Math.round(Fall * 1000) / 1000;

        if (Fall < 5) {
            flagfall = true;
        }

        if ((Fall > 19) && (flagfall == true)) {
            flaghit = true;
        }
        if ((k < 40) && (flaghit == true)) {
            if ((Fall > 8) && (Fall < 11)) {

                flagfall = false;
                flaghit = false;
                k = 0;
                Intent intentAlarm = new Intent(MainActivity.this, Alarm.class);
                startActivity(intentAlarm);
            }

        } else k++;

        if (k>30) {
            flagfall = false;
            flaghit = false;
            k = 0;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}