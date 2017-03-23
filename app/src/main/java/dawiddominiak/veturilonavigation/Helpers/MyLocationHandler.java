package dawiddominiak.veturilonavigation.Helpers;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Dawid Dominiak on 2017-03-04.
 */

public class MyLocationHandler implements LocationListener {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude = (location.getLatitude());
        this.longitude = (location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
