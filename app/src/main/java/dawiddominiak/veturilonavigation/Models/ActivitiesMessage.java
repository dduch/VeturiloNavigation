package dawiddominiak.veturilonavigation.Models;

import java.io.Serializable;

/**
 * Created by Dawid Dominiak on 2017-03-18.
 */

public class ActivitiesMessage implements Serializable {
    public NavigationResult Result;

    public NavLocation StartPosition;

    public NavLocation Destination;
}
