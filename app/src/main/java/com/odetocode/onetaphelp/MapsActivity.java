package com.odetocode.onetaphelp;

import android.app.DownloadManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.HttpsURLConnection;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private ArrayList<Marker> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        String azure = "http://onetaphelpbackend.azurewebsites.net/api/values";
        String url = "https://onetaphelpbackend.azurewebsites.net/api/values";

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //You can test it by printing response.substring(0,500) to the screen.
                Log.i("App","Got response "+response);
                String getdata = response;

                if (getdata != null){
                    Log.d("app","not null");
                    Log.d("app",getdata);

                    String[] aa = getdata.split("_");

                    for (int i=0;i<aa.length;i++)
                    {
                        String bb[] = aa[i].split("_");
                        long a,b,c,d;
                        a = b =c =d =0;
                        Log.i("App",aa[i]);/*
                        for (int ii = 0;ii < bb.length;ii++)
                        {
                            Log.i("App",bb[ii]);
                        }*/
                        try
                        {
                            a = Long.parseLong(bb[0]);
                            b = Long.parseLong(bb[1]);
                            c = Long.parseLong(bb[2]);
                            d = Long.parseLong(bb[3]);
                        }
                        catch (Exception e)
                        {

                        }
                        list.add(new Marker(a,b,c,d));
                    }
                    for(int j=0;j<list.size();j++)
                    {

                        LatLng sydne = new LatLng((list.get(j).latitude)/100000000.0, (list.get(j).longitude)/100000000.0);
                        mMap.addMarker(new MarkerOptions().position(sydne).title(list.get(j).number + ""));
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.i("App","ERROR VOLLEY");
            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);

        String getdata = null;

        if (getdata != null){
            Log.d("app","not null");
            Log.d("app",getdata);
            String a[] = getdata.split("|");
            for (int i=0;i<a.length;i++)
            {
                String b[] = a[i].split("_");
                list.add(new Marker(Long.parseLong(b[0]),Long.parseLong(b[1]),Long.parseLong(b[2]),Long.parseLong(b[3])));
            }
            for(int j=0;j<list.size();j++)
            {

                LatLng sydne = new LatLng((list.get(j).latitude)/100000000.0, (list.get(j).longitude)/100000000.0);
                mMap.addMarker(new MarkerOptions().position(sydne).title(list.get(j).number + ""));
            }
        }

    }
}
