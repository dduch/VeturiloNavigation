package dawiddominiak.veturilonavigation.AsynTasks;

import android.os.AsyncTask;

import dawiddominiak.veturilonavigation.Activities.UserInputActivity;
import dawiddominiak.veturilonavigation.Constants.Constants;
import dawiddominiak.veturilonavigation.Models.NavLocation;
import dawiddominiak.veturilonavigation.Models.NavigationResult;
import dawiddominiak.veturilonavigation.Models.RequestDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dawid Dominiak on 2017-03-17.
 */

public class ServiceCallerTask extends AsyncTask<Void, String, Void>{
    private UserInputActivity activity;
    private NavLocation startPosition;
    private NavLocation destination;
    private int speed;
    private int people;

    public ServiceCallerTask(NavLocation start, NavLocation end, int speed, int people, UserInputActivity activity){
        this.startPosition = start;
        this.destination = end;
        this.speed = speed;
        this.people = people;
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String result = null;
        try {

            RequestDTO requestDTO = PrepareRequest();
            Gson gson = new Gson();
            String json = gson.toJson(requestDTO);

            HttpURLConnection httpcon = (HttpURLConnection) ((new URL(Constants.NAVIGATION_SERVICE_URL).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

            //Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json);
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            String newString = result.replace("\\", "");
            newString = newString.substring(1, newString.length() - 1);
            NavigationResult container = new Gson().fromJson(newString, NavigationResult.class);
            activity.setNavigationResult(container);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected  RequestDTO PrepareRequest(){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setStartPositionLatitude(startPosition.getLatitue());
        requestDTO.setStartPositionLongitude(startPosition.getLongitude());
        requestDTO.setDestinationPositionLatitude(destination.getLatitue());
        requestDTO.setDestinationPositionLongitude(destination.getLongitude());
        requestDTO.setSpeed(speed);

        return requestDTO;
    }
}


