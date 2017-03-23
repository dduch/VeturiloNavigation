package dawiddominiak.veturilonavigation.AsynTasks;

import android.os.AsyncTask;
import android.util.Log;

import dawiddominiak.veturilonavigation.Models.NavLocation;
import dawiddominiak.veturilonavigation.Helpers.PlaceJSONParser;
import dawiddominiak.veturilonavigation.Helpers.UrlDownloader;
import dawiddominiak.veturilonavigation.Activities.UserInputActivity;

/**
 * Created by Dawid Dominiak on 2017-03-08.
 */

public class GeocoderTask extends AsyncTask<String, Void, NavLocation> {
    private UserInputActivity activity;
    private String placeToGeocode;
    private boolean isStartPostition;


    public GeocoderTask(String place, UserInputActivity activity, boolean isStartPosition){
        this.activity = activity;
        this.placeToGeocode = place;
        this.isStartPostition = isStartPosition;
    }

    @Override
    protected NavLocation doInBackground(String... key) {
        // For storing data from web service
        String data = "";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + placeToGeocode + "&" + key[0];

        try{
            // Fetching the data from web service
            data = new UrlDownloader().DownloadUrl(url.replaceAll(" ", "%20"));
        }catch(Exception e){
            Log.d("Background Task",e.toString());
        }

        NavLocation location = new PlaceJSONParser().ParseGeocodedInfo(data);
        location.setLocationName(placeToGeocode);

        if(isStartPostition){
            this.activity.setStartPosition(location);
        }
        else{
            this.activity.setDestination(location);
        }

        return location;
    }
}
