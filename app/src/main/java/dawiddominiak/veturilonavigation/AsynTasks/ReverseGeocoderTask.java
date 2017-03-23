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

public class ReverseGeocoderTask extends AsyncTask<String, Void, String> {
    private double latitude;
    private double longitude;
    private UserInputActivity activity;

    @Override
    protected void onPostExecute(String s) {

    }

    public ReverseGeocoderTask(double latitude, double longitude, UserInputActivity activity){
        this.latitude = latitude;
        this.longitude = longitude;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... key) {
        // For storing data from web service
        String data = "";

        // Building the url to the web service
       String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&result_type=street_address&" + key[0];

        try{
            // Fetching the data from we service
            data = new UrlDownloader().DownloadUrl(url);
        }catch(Exception e){
            Log.d("Background Task",e.toString());
        }

        String startPlace = new PlaceJSONParser().ParseReverseGeocodedInfo(data);
        NavLocation startLocation = new NavLocation(this.latitude, this.longitude, startPlace);
        this.activity.UpdateStartPosition(startLocation);
        return data;
    }

}
