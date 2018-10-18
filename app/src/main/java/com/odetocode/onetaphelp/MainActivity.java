package com.odetocode.onetaphelp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionRequest permissionRequest = new PermissionRequest(this);
        permissionRequest.startRequestingPermissions();

        if (permissionRequest.getPermissionStatus())
        {
            Toast.makeText(this, "All permissions granted", Toast.LENGTH_LONG);
            Intent intent = new Intent(this,StartPageActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Permissions Denied", Toast.LENGTH_LONG);
        }
    }

    public void sendSMS(String phoneNo, String msg)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
