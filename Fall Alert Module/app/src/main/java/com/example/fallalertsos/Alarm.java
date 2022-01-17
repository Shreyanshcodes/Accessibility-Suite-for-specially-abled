package com.example.fallalertsos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class Alarm extends AppCompatActivity {
    public static String phone;
    String pn1,pn2 ,pn3,pn4 ;
    private static final long START_TIME_IN_MILLIS = 15000; //15sec
    private TextView mTextViewCountdown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    boolean flagCall = false;
    private Ringtone r;
    DatabaseHelper DB;
    int id_To_Update = 0;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        DB = new DatabaseHelper(this);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();


        mTextViewCountdown = findViewById(R.id.text_view_countdown);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                Alarm.this
        );
        startTimer();

        Button buttonProblem = findViewById(R.id.buttonProblem);
        buttonProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Alarm.this, "ALERT SOS GENERATED !", Toast.LENGTH_SHORT).show();
                int Value = 1;
                if (Value > 0) {
                    //means this is the view part not the add contact part.

                    Cursor rs = DB.getData(Value);
                    id_To_Update = Value;
                    rs.moveToFirst();

                    pn1 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn1));
                    pn2 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn2));
                    pn3 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn3));
                    pn4 = rs.getString(rs.getColumnIndex(DatabaseHelper.CONTACTS_COLUMN_pn4));

                    if (!rs.isClosed()) {
                        rs.close();
                        Toast.makeText(Alarm.this, "HELLO", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Alarm.this, pn1 + pn2 + pn3 + pn4, Toast.LENGTH_SHORT).show();
                }
                if(ActivityCompat.checkSelfPermission(Alarm.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Alarm.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else{
                    ActivityCompat.requestPermissions(Alarm.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
                phone = "00000";
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+phone));

                if (ActivityCompat.checkSelfPermission(Alarm.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                flagCall = true;

                r.stop();
                finish();
                startActivity(phoneIntent);
            }
        });

        Button buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flagCall=true;//to prevent from calling
                r.stop();
                finish();

            }
        });


    }


    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

            }
        }.start();

        mTimerRunning = true;
    }

    @SuppressLint("MissingPermission")
    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        mTextViewCountdown.setText(timeLeftFormatted);

        if ((seconds == 0)&&(flagCall==false)) {
            if(ActivityCompat.checkSelfPermission(Alarm.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Alarm.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else{
                ActivityCompat.requestPermissions(Alarm.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
            }
//            phone = getPhone();
            phone = "0000";
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+phone));

            if (ActivityCompat.checkSelfPermission(Alarm.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            r.stop();
            finish();
            startActivity(phoneIntent);
            //send sms
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100 && grantResults.length>0 && (grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }
        else{
            Toast.makeText(getApplicationContext(),"PERMISSIONS DENIED",Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if(location!=null){

                        Double LAT = location.getLatitude();
                        Double LONG =  location.getLongitude();


                        String linkaddress = "https://www.google.com/maps/place/?q="+LAT+","+LONG;
                        String finalsms = "Need Help! Track Me"+"\n"+linkaddress+"\n"+"Accessibility suite Fall detected";
                        try {
                            SmsManager sms = SmsManager.getDefault(); // using android SmsManager

                            sms.sendTextMessage(pn1, null, finalsms, null, null);
                            sms.sendTextMessage(pn2, null, finalsms, null, null);
                            sms.sendTextMessage(pn3, null, finalsms, null, null);
                            sms.sendTextMessage(pn4, null, finalsms, null, null);
                        }
                        catch (Exception e){
                            e.printStackTrace();

                            Toast.makeText(Alarm.this, "FAILED TO SEND SMS", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                Double LAT1 = location1.getLatitude();
                                Double LONG1 =  location1.getLongitude();

                                String linkaddress = "https://www.google.com/maps/place/?q="+LAT1+","+LONG1;
                                String finalsms = "Need Help! Track Me"+"\n"+linkaddress;
//                                String phonenumber1 ="+916265364241";
                                try {
                                    SmsManager sms = SmsManager.getDefault(); // using android SmsManager
//                                    sms.sendTextMessage(phonenumber1, null, finalsms, null, null);
                                    sms.sendTextMessage(pn1, null, finalsms, null, null);
                                    sms.sendTextMessage(pn2, null, finalsms, null, null);
                                    sms.sendTextMessage(pn3, null, finalsms, null, null);
                                    sms.sendTextMessage(pn4, null, finalsms, null, null);
                                }
                                catch (Exception e){
                                    e.printStackTrace();

                                    Toast.makeText(Alarm.this, "FAILED TO SEND SMS", Toast.LENGTH_SHORT).show();
                                }

                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }
                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }



}

