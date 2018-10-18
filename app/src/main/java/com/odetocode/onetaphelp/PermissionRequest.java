package com.odetocode.onetaphelp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class PermissionRequest
{
    private boolean smsPermission;
    private boolean gpsPermission;
    private AppCompatActivity activity;

    public PermissionRequest(AppCompatActivity activity)
    {
        this.activity = activity;
    }

    public void startRequestingPermissions()
    {
        smsPermission = getSmsPermission();
        gpsPermission = getGpsPermission();
        Toast.makeText(activity,smsPermission+" "+gpsPermission,Toast.LENGTH_LONG);
        Log.i("App",smsPermission+" "+gpsPermission);
        if (!smsPermission)
        {
            requestSmsPermission();
        }
        if (!gpsPermission)
        {
            requestGpsPermission();
        }
    }
    private void requestSmsPermission() {
        // request permission (see result in onRequestPermissionsResult() method)
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS},123 );
    }
    private void requestGpsPermission(){
        LocationManager service = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }
    private boolean getSmsPermission()
    {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean getGpsPermission()
    {
        LocationManager service = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public boolean getPermissionStatus()
    {
        return (smsPermission && gpsPermission);
    }
}
