package dawiddominiak.veturilonavigation.AsynTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.util.concurrent.ExecutionException;

import dawiddominiak.veturilonavigation.Activities.NavigationActivity;
import dawiddominiak.veturilonavigation.Activities.UserInputActivity;
import dawiddominiak.veturilonavigation.Constants.Constants;
import dawiddominiak.veturilonavigation.Models.ActivitiesMessage;

/**
 * Created by Dawid Dominiak on 2017-03-18.
 */

public class ProgressTask extends AsyncTask<Void, Void, Void> {
    public ProgressDialog mDialog;
    public UserInputActivity mContext;

    public ProgressTask(UserInputActivity context) {
        super();
        mContext = context;
    }

    protected void onPreExecute() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Finding best route for you, please wait...");
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    protected void onPostExecute(Void result) {
        //mContext = null;
        mDialog.dismiss();
        Intent intent = new Intent(mContext, NavigationActivity.class);
        ActivitiesMessage msg = new ActivitiesMessage();
        msg.StartPosition = mContext.getStartPosition();
        msg.Destination = mContext.getDestination();
        msg.Result = mContext.getNavigationResult();

        intent.putExtra("dawiddominiak.veturilonavigation.Models.ActivitiesMessage", msg);
        mContext.startActivity(intent);
    }

    @Override
    protected Void doInBackground(Void... params) {
        GeocoderTask geocoder = new GeocoderTask(mContext.getStartPosition().getLocationName(), mContext, true);

        try {
            geocoder.doInBackground(Constants.REVERSE_GEOCODING_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        geocoder = new GeocoderTask(mContext.getDestination().getLocationName(), mContext, false);
        try {
            geocoder.doInBackground(Constants.REVERSE_GEOCODING_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServiceCallerTask task = new ServiceCallerTask(mContext.getStartPosition(), mContext.getDestination(),
                mContext.getSpeed(), 1, mContext);
        try {
            task.doInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

