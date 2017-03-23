package dawiddominiak.veturilonavigation.Activities;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;

import dawiddominiak.veturilonavigation.Models.NavigationResult;
import dawiddominiak.veturilonavigation.AsynTasks.PlacesTask;
import dawiddominiak.veturilonavigation.AsynTasks.ProgressTask;
import dawiddominiak.veturilonavigation.AsynTasks.ReverseGeocoderTask;
import dawiddominiak.veturilonavigation.Constants.Constants;
import dawiddominiak.veturilonavigation.Helpers.MyLocationHandler;
import dawiddominiak.veturilonavigation.Models.NavLocation;
import dawiddominiak.veturilonavigation.R;


public class UserInputActivity extends AppCompatActivity {
    private final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1100;
    private NavLocation startPosition;
    private NavLocation destination;
    private boolean isStartPositionSet = false;
    private boolean isDestinationSet = false;
    private Location currentPosition;
    private NavigationResult navigationResult;
    private AutoCompleteTextView startPositionField;
    private AutoCompleteTextView destinationField;
    private MyLocationHandler locationHandler;
    private LocationManager locationManager;
    private Switch myLocationSwitch;
    private SeekBar speedBar;
    private ProgressTask mTask = null;

    public NavigationResult getNavigationResult() {
        return navigationResult;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int speed = 0;

    public NavLocation getStartPosition() {
        return startPosition;
    }

    public NavLocation getDestination() {
        return destination;
    }

    public boolean isStartPositionSet() {
        return isStartPositionSet;
    }

    public void setDestination(NavLocation destination) {
        this.destination = destination;
    }

    public void setStartPosition(NavLocation startPosition) {
        this.startPosition = startPosition;
    }

    public void setNavigationResult(NavigationResult navigationResult) {
        this.navigationResult = navigationResult;
    }

    public Location getCurrentPosition() {
        return currentPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        locationHandler = new MyLocationHandler();

        startPositionField = (AutoCompleteTextView) findViewById(R.id.startPositionFieldId);
        startPositionField.setThreshold(3);

        destinationField = (AutoCompleteTextView) findViewById(R.id.destinationFieldId);
        destinationField.setThreshold(3);

        myLocationSwitch = (Switch) findViewById(R.id.UseMyLocationId);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        speedBar = (SeekBar) findViewById(R.id.speedBarId);

        startPositionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PlacesTask placesTask = new PlacesTask(startPositionField, UserInputActivity.this);
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        startPositionField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((HashMap<String, String>)parent.getItemAtPosition(position)).get("description");
                startPosition = new NavLocation(selectedItem);
            }
        });

        destinationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PlacesTask placesTask = new PlacesTask(destinationField, UserInputActivity.this);
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        destinationField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((HashMap<String, String>)parent.getItemAtPosition(position)).get("description");
                destination = new NavLocation(selectedItem);
            }
        });

        Button navigateMe = (Button) findViewById(R.id.StartNavigateId);
        navigateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try{
                ProgressTask task = (ProgressTask)getLastNonConfigurationInstance();
                if (task != null) {
                    mTask = task;
                    mTask.mContext = UserInputActivity.this;
                    mTask.mDialog =  new ProgressDialog(UserInputActivity.this);
                    mTask.mDialog.setMessage("Please wait...");
                    mTask.mDialog.setIndeterminate(false);
                    mTask.mDialog.setMax(100);
                    mTask.mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mTask.mDialog.setCancelable(true);
                    mTask.mDialog.show();
                }

                mTask = new ProgressTask(UserInputActivity.this);
                mTask.execute();
            }
            catch (Exception ex) {

            }

            }
        });



        myLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(UserInputActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(UserInputActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UserInputActivity.this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                                MY_PERMISSION_ACCESS_COARSE_LOCATION);
                        return;
                    }
                    else {
                        currentPosition = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationHandler);
                        ReverseGeocoderTask geocoder = new ReverseGeocoderTask(currentPosition.getLatitude(), currentPosition.getLongitude(), UserInputActivity.this);
                        geocoder.execute(Constants.REVERSE_GEOCODING_KEY);
                    }
                }
            }
        });

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                speed = progress;
                TextView textView = (TextView) findViewById(R.id.estimateSpeedId);
                textView.setText("Estimate speed: " + progress + " [km/h]");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationHandler);
                }

                return;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void UpdateStartPosition(final NavLocation location){
        this.startPosition = location;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView) findViewById(R.id.startPositionFieldId);
                textView.setText(location.getLocationName());
            }
        });
    }
}