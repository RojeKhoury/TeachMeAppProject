package com.example.teachmeapp.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.teachmeapp.HomePageStudent;
import com.example.teachmeapp.HomePageTeacher;
import com.example.teachmeapp.R;
import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class MapsFragmentChooseLocation extends Fragment implements OnMapReadyCallback {


    private static MapsFragmentChooseLocation INSTANCE = null;
    String TAG = "PLACE";
    View view;

    GoogleMap map;
    MapView mapView;
    SearchView searchView;
    Marker m_mark;
    Button m_chooseLocation;
    FusedLocationProviderClient m_location;

    public MapsFragmentChooseLocation() {
    }

    public static MapsFragmentChooseLocation getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new MapsFragmentChooseLocation();
        return INSTANCE;
    }

    @Override
    public void onCreate(@javax.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps_choose_location, container, false);
        m_location = LocationServices.getFusedLocationProviderClient(view.getContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Places.
        Places.initialize(getActivity(), "AIzaSyCx8KKk_OHGhFXomk3izCBCYmWM0jMPqoM");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity());
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList( Place.Field.LAT_LNG));


        mapView = view.findViewById(R.id.mapsViewChoose);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                LatLng loc = place.getLatLng();
                List<Address> addresses;
                Geocoder geocoder= new Geocoder(view.getContext(), Locale.getDefault());
                String city="";

                try {
                    addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    city = addresses.get(0).getLocality();

                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    if (m_mark != null) {
                        m_mark.remove();
                    }
                    m_mark = map.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getLocality()));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(!city.isEmpty())
                {
                    Log.i(TAG, city);
                }
                else
                {
                    Toast.makeText(getActivity(), "Try entering another country/state/city please!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        m_chooseLocation = view.findViewById(R.id.choose_location_button);
        m_chooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng loc = m_mark.getPosition();
                Geocoder geocoder = new Geocoder(view.getContext(), Locale.getDefault());
                List<Address> addresses;
                try {
                   addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    // Used for debugging why always get address in the database 'null'.
                    /*String addressline = "Addresses from getAddressLine(): ";
                    for (int n = 0; n <= addresses.get(0).getMaxAddressLineIndex(); n++) {
                        addressline += " index n: " + n + ": " + addresses.get(0).getAddressLine(n) + ", ";
                    }
                    Log.d("Addresses: ", addressline);*/

                    // Using getAddressLine(0) will retrieve the right address. No more 'null' value
                    comm.setLocation(loc, addresses.get(0).getLocality(), addresses.get(0).getCountryName(), addresses.get(0).getAddressLine(0));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent;

                if(comm.isTeacher())
                {intent= new Intent(getActivity(), HomePageTeacher.class);}

                else
                {intent = new Intent(getActivity(), HomePageStudent.class);}

                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        MapsInitializer.initialize(getContext());
        map = googleMap;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        m_location.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(view.getContext(),
                                Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        m_mark = map.addMarker(new MarkerOptions().position(latLng).title("my location"));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /*private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
       /* @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }*/
}
