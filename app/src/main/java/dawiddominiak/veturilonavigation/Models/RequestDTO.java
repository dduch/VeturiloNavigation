package dawiddominiak.veturilonavigation.Models;

import java.security.PublicKey;

/**
 * Created by Dawid Dominiak on 2017-03-17.
 */

public class RequestDTO {
    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public double getStartPositionLatitude() {
        return StartPositionLatitude;
    }

    public void setStartPositionLatitude(double startPositionLatitude) {
        StartPositionLatitude = startPositionLatitude;
    }

    public double getStartPositionLongitude() {
        return StartPositionLongitude;
    }

    public void setStartPositionLongitude(double startPositionLongitude) {
        StartPositionLongitude = startPositionLongitude;
    }

    public double getDestinationPositionLatitude() {
        return DestinationPositionLatitude;
    }

    public void setDestinationPositionLatitude(double destinationPositionLatitude) {
        DestinationPositionLatitude = destinationPositionLatitude;
    }

    public double getDestinationPositionLongitude() {
        return DestinationPositionLongitude;
    }

    public void setDestinationPositionLongitude(double destinationPositionLongitude) {
        DestinationPositionLongitude = destinationPositionLongitude;
    }

    private double StartPositionLatitude;

    private double StartPositionLongitude;

    private double DestinationPositionLatitude;

    private double DestinationPositionLongitude;

    private double Speed;

}
