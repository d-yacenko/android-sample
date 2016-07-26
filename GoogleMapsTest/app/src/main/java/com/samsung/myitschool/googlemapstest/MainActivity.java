package com.samsung.myitschool.googlemapstest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    GoogleMap googleMap;
    EditText coord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coord=(EditText)findViewById(R.id.coord);
        createMapView();
    }
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(47.222531,39.718705))
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    public void go(View view) {
        if(null != googleMap){
            String[] latLng = coord.getText().toString().split(",");
            double latitude = Double.parseDouble(latLng[0]);
            double longitude = Double.parseDouble(latLng[1]);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude,longitude))
                    .title("Mark")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fire_icon))
                    .draggable(false)
            );
        }
    }
}
