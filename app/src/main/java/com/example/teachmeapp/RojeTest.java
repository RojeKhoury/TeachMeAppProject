package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.api.Places;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.teachmeapp.Helpers.Globals.comm;


public class RojeTest  extends AppCompatActivity {
    String TAG = "PLACE";
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roje_test );
        txtView = findViewById(R.id.txtView);
        Log.d(TAG,"created");
        //Dont tuch doumentation for Roje https://developers.google.com/places/android-sdk/autocomplete#filter_results_by_place_type
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyCx8KKk_OHGhFXomk3izCBCYmWM0jMPqoM");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList( Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                LatLng loc = place.getLatLng();
                List<Address> addresses;
                Geocoder geocoder= new Geocoder(txtView.getContext(), Locale.getDefault());
                String city="";
                try {
                    addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    city = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!city.isEmpty())
                {
                    Log.i(TAG, city);
                }
                else
                {
                    //add failed option
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}