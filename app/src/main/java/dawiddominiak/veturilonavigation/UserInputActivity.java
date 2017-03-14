package dawiddominiak.veturilonavigation;

import android.content.Intent;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import static android.R.attr.value;


public class UserInputActivity extends AppCompatActivity {

    private final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1100;
    private NavLocation startPosition;
    private NavLocation destination;
    private Location currentPosition;

    private AutoCompleteTextView startPositionField;
    private AutoCompleteTextView destinationField;
    private MyLocationHandler locationHandler;
    private LocationManager locationManager;
    private Switch myLocationSwitch;

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

        startPositionField.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int a = 3;
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

        destinationField.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int a = 3;
            }
        });

        Button navigateMe = (Button) findViewById(R.id.StartNavigateId);
        navigateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), NavigationActivity.class);
                view.getContext().startActivity(Intent);
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
                        ReverseGeocoder geocoder = new ReverseGeocoder(currentPosition.getLatitude(), currentPosition.getLongitude(), UserInputActivity.this);
                        geocoder.execute(Constants.REVERSE_GEOCODING_KEY);
                    }
                }
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