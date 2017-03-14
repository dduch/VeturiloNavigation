package dawiddominiak.veturilonavigation;

/**
 * Created by Dawid Dominiak on 2017-02-26.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

/** A class to parse the Google Places in JSON format */
public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

    private AutoCompleteTextView autoCompleteWidget;
    private JSONObject jObject;
    private Activity activity;

    public ParserTask(AutoCompleteTextView widget, Activity parentActivity){
        this.autoCompleteWidget = widget;
        this.activity = parentActivity;
    }

    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {
        List<HashMap<String, String>> places = null;
        PlaceJSONParser placeJsonParser = new PlaceJSONParser();

        try{
            jObject = new JSONObject(jsonData[0]);

            // Getting the parsed data as a List construct
            places = placeJsonParser.parse(jObject);

        }catch(Exception e){
            Log.d("Exception",e.toString());
        }
        return places;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> result) {
        String[] from = new String[] { "description"};
        int[] to = new int[] { android.R.id.text1 };

        // Creating a SimpleAdapter for the AutoCompleteTextView
        SimpleAdapter adapter = new SimpleAdapter(activity.getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);

        // Setting the adapter
        autoCompleteWidget.setAdapter(adapter);
    }
}