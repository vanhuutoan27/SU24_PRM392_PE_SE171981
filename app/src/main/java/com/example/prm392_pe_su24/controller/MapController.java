package com.example.prm392_pe_su24.controller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapController {
    private GoogleMap googleMap;
    private Context context;

    public MapController(GoogleMap googleMap, Context context) {
        this.googleMap = googleMap;
        this.context = context;
    }

    public void updateLocation(String address) {
        if (address != null && !address.isEmpty()) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocationName(address, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address location = addresses.get(0);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                } else {
                    Toast.makeText(context, "Unable to find location for the given address", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error finding location", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No address provided", Toast.LENGTH_SHORT).show();
        }
    }
}
