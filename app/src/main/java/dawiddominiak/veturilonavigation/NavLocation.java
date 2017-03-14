package dawiddominiak.veturilonavigation;

/**
 * Created by Dawid Dominiak on 2017-03-08.
 */

public class NavLocation {
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

    public NavLocation(double latitue, double longitude){
        this.latitue = latitue;
        this.longitude = longitude;
        CoordinatesToName();
    }

    public NavLocation(double latitue, double longitude, String name){
        this.latitue = latitue;
        this.longitude = longitude;
        this.locationName = name;
    }

    public void CoordinatesToName(){

    }
}
