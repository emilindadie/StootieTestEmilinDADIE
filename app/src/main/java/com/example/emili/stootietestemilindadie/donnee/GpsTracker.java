package com.example.emili.stootietestemilindadie.donnee;

/**
 * Created by emili on 09/08/2017.
 */


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by emili on 20/08/2017.
 */

public class GpsTracker implements LocationListener {

    Context context;

    public GpsTracker(Context context) {
        super();
        this.context = context;
    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist","error");
            return null;
        }
        try {
            LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled){
                Criteria criteria = new Criteria();
                criteria.setPowerRequirement(Criteria.ACCURACY_LOW);
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setSpeedRequired(true);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(false);

                String provider = lm.getBestProvider(criteria, true);
                //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000,10,this);
                lm.requestLocationUpdates(provider, 6000,10,this);
                // Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                return lm.getLastKnownLocation(provider);
            }else{
                Log.e("sec","errpr");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
