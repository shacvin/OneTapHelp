package com.odetocode.onetaphelp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;

public class LoadingActivity extends AppCompatActivity
{
    static LoadingActivity activity;
    Button cancelButton;
    LocationListener listener;
    private boolean messageSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        activity = this;
        listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                String string = "MCCWW ";
                string += (long)(location.getLongitude()*100000000);
                string += "_";
                string += (long)(location.getLatitude()*100000000);
                Console.print("Send start");
                if (!messageSent)
                {
                    sendSMS("9220592205", string);
                    messageSent = true;
                }
                Intent intent = new Intent(LoadingActivity.activity,OnSmsSendActivity.class);
                Console.print("Send end");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

    }

    @Override
    public void onStart()
    {
        super.onStart();
        messageSent = false;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        }
        else
        {
            Console.print("Permission for location");
        }

        Console.print("GPS started");

    }

    public void onCancel()
    {
        Intent intent = new Intent(this, StartPageActivity.class);
        startActivity(intent);
    }

    public void sendSMS(String phoneNo, String msg)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,OnSmsSendActivity.class);
            startActivity(i);
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
