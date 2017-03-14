package dawiddominiak.veturilonavigation;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
        String key = "key=AIzaSyDjJkTNpjWI3UAAJ78HycQ_2-Ow50mnncw";

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