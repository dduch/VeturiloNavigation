package dawiddominiak.veturilonavigation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dawid Dominiak on 2017-03-17.
 */

public class NavigationResult implements Serializable {
    @SerializedName("Waypoints")
    @Expose
    public Waypoint[] Waypoints; // Geolocations of subsequent points of route

    @SerializedName("Keypoints")
    @Expose
    public Keypoint[] Keypoints; // Metadata aobut special waypoints (change stations, startpoint, endpoint)

    @SerializedName("RouteLength")
    @Expose
    public double RouteLength;   // Route total length in meters

    @SerializedName("EstimatedCost")
    @Expose
    public double EstimatedCost;  // Route total cost in PLN
}
