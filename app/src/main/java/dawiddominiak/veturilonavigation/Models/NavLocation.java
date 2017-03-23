package dawiddominiak.veturilonavigation.Models;

import java.io.Serializable;

/**
 * Created by Dawid Dominiak on 2017-03-08.
 */

public class NavLocation implements Serializable {
    public double getLatitue() {
        return latitue;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    private double latitue;
    private double longitude;
    private String locationName;

    public NavLocation(String name) {
        this.locationName = name;
    }

    public NavLocation(double latitue, double longitude){
        this.latitue = latitue;
        this.longitude = longitude;
    }

    public NavLocation(double latitue, double longitude, String name){
        this.latitue = latitue;
        this.longitude = longitude;
        this.locationName = name;
    }
}
