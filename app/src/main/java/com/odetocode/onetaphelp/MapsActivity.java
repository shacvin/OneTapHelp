package com.odetocode.onetaphelp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.net.ssl.HttpsURLConnection;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
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

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
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
