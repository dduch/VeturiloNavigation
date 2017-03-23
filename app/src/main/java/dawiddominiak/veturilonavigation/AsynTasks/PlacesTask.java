package dawiddominiak.veturilonavigation.AsynTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import dawiddominiak.veturilonavigation.Helpers.UrlDownloader;

/**
 * Created by Dawid Dominiak on 2017-02-26.
 */

// Fetches all places from GooglePlaces AutoComplete Web Service
public class PlacesTask extends AsyncTask<String, Void, String> {

    private AutoCompleteTextView autoCompleteWidget;
    private Activity activity;
    private ParserTask parserTask;

    public PlacesTask(AutoCompleteTextView widget, Activity parentActivity) {
        this.autoCompleteWidget = widget;
        this.activity = parentActivity;
    }

    @Override
    protected String doInBackground(String... place) {
        // For storing data from web service
        String data = "";

        // Obtain browser key from https://code.google.com/apis/console
        String key = "key=AIzaSyCzCPTa9w1ekENLIDRQk69woBxesIW_KVM";

        String input="";

        try {
            input = "input=" + URLEncoder.encode(place[0], "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String types = "types=geocode";
        String sensor = "sensor=false";
        String location = "location=52.22967560,21.01222870";
        String radius = "radius=20000";

        String parameters = input+"&"+types+"&"+sensor+"&"+location+"&"+radius+"&strictbounds"+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

        try{
            // Fetching the data from we service
            data = new UrlDownloader().DownloadUrl(url);
        }catch(Exception e){
            Log.d("Background Task",e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Creating ParserTask
        parserTask = new ParserTask(autoCompleteWidget, this.activity);

        // Starting Parsing the JSON string returned by Web Service
        parserTask.execute(result);
    }


}