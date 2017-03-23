package dawiddominiak.veturilonavigation.Models;

import java.io.Serializable;

/**
 * Created by Dawid Dominiak on 2017-03-19.
 */

// Metadata about special waypoints - stations or start/end points
public class Keypoint implements Serializable
{
    public boolean IsStation; // True if keypoint is station
    public int WaypointIndex; // Index of keypoint in waypoints array
    public String Name;       // Keypoint specific name
    public int Number;        // Station unique identifier (-1 if keypoint is not station)
    public double DistanceFromPrevious; // Distance from previous keypoint to this in meters
    public double CostFromPrevious;  // Cost of getting to this keypoint from previous in PLN
}
