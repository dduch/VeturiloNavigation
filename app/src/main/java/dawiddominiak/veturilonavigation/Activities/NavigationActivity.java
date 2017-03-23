package dawiddominiak.veturilonavigation.Activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import dawiddominiak.veturilonavigation.Models.ActivitiesMessage;
import dawiddominiak.veturilonavigation.R;

public class NavigationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle b = getIntent().getExtras();
        ActivitiesMessage msg = (ActivitiesMessage) getIntent().getExtras().get("dawiddominiak.veturilonavigation.Models.ActivitiesMessage");

        LatLng startPoint = new LatLng(msg.StartPosition.getLatitue(), msg.StartPosition.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));

        PolylineOptions rectOptions = new PolylineOptions();

        for(int i = 0; i < msg.Result.Waypoints.length; ++i){
            rectOptions.add(new LatLng(msg.Result.Waypoints[i].Latitude , msg.Result.Waypoints[i].Longitude));
        }

        for(int i = 0; i < msg.Result.Keypoints.length; ++i){
            LatLng position = new LatLng(msg.Result.Waypoints[msg.Result.Keypoints[i].WaypointIndex].Latitude , msg.Result.Waypoints[msg.Result.Keypoints[i].WaypointIndex].Longitude);

            BitmapDescriptor icon;
            if(i == 0 || (i == msg.Result.Keypoints.length - 1)){
                icon = BitmapDescriptorFactory.fromResource(R.drawable.markerblue);
            }
            else{
                icon = BitmapDescriptorFactory.fromResource(R.drawable.markergreen);
            }

            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(msg.Result.Keypoints[i].Name)
                    .icon(icon));
        }

        rectOptions.color(Color.BLUE);
        rectOptions.width(12.5f);
        // Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);

    }

}
