package com.matictechnology.boss.Acivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.matictechnology.boss.R;

import java.util.ArrayList;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener
{

    LocationListener ltis = this;
    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location mCurrentLocation;//,friendlocation;
    private GoogleApiClient mGoogleApiClient;
    private final int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 0;
    Spinner list;
    ArrayList<String> alist;
    //ArrayList<Double> friendlistlat,friendlistlong;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        //friendlistlat=new ArrayList<>();
        //friendlistlong=new ArrayList<>();


        mGoogleApiClient = new GoogleApiClient.Builder( ActivityMaps.this )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.satellite)
        {
            if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityMaps.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initCamera(mCurrentLocation, 0);
            //handling menu options and opening the contact us page
        }
        if (id == R.id.normal)
        {
            if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityMaps.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initCamera(mCurrentLocation, 1);
            //handling menu options and opening the contact us page
        }
        if (id == R.id.hybrid)
        {
            if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityMaps.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initCamera(mCurrentLocation, 2);
            //handling menu options and opening the contact us page
        }
        if (id == R.id.terrain)
        {
            if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityMaps.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initCamera(mCurrentLocation, 3);
            //handling menu options and opening the contact us page
        }
        if (id == R.id.none)
        {
            if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityMaps.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initCamera(mCurrentLocation, 4);
            //handling menu options and opening the contact us page
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCamera(Location location,int curMapTypeIndex1)
    {
        if (location != null)
        {
            Log.e("TAG", "GPS is on");
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(),
                            location.getLongitude()))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();

            LatLng myloc=new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myloc).title("this is me!")).setIcon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), null);

            mMap.setMapType(MAP_TYPES[curMapTypeIndex1]);
            mMap.setTrafficEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
        else
        {
            //This is what you need:
            LocationManager locationManager = (LocationManager) ActivityMaps.this.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
            if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityMaps.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            locationManager.requestLocationUpdates(bestProvider, 1000, 0, ltis);
            if (locationManager== null) return;
            String provider = locationManager.getBestProvider(criteria, true);
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(locationManager.getLastKnownLocation(provider).getLatitude(),
                            locationManager.getLastKnownLocation(provider).getLongitude()))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();

            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), null);

            mMap.setMapType(MAP_TYPES[curMapTypeIndex1]);
            mMap.setTrafficEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ActivityMaps.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //Toast.makeText(ActivityMaps.this, ""+mCurrentLocation.getLatitude()+","+mCurrentLocation.getLongitude() , Toast.LENGTH_LONG).show();
        //initCamera(mCurrentLocation, 0);
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ActivityMaps.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        try
        {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        //friendlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //friendlocation.setLatitude(mCurrentLocation.getLatitude() - 0.0004);
        //friendlocation.setLongitude(mCurrentLocation.getLongitude() - 0.0004);

        //friendlistlat.add(friendlocation.getLatitude());
        //friendlistlong.add(friendlocation.getLongitude());

        //friendlocation.setLatitude(mCurrentLocation.getLatitude() + 0.0004);
        //friendlocation.setLongitude(mCurrentLocation.getLongitude() + 0.0004);

        //friendlistlat.add(friendlocation.getLatitude());
        //friendlistlong.add(friendlocation.getLongitude());

        //friendlocation.setLatitude(mCurrentLocation.getLatitude() + 0.0006);
        //friendlocation.setLongitude(mCurrentLocation.getLongitude() - 0.0004);

        //friendlistlat.add(friendlocation.getLatitude());
        //friendlistlong.add(friendlocation.getLongitude());

        //Toast.makeText(ActivityMaps.this, ""+mCurrentLocation.getLatitude()+","+mCurrentLocation.getLongitude() , Toast.LENGTH_LONG).show();
        //Toast.makeText(ActivityMaps.this, ""+friendlocation.getLatitude()+","+friendlocation.getLongitude() , Toast.LENGTH_LONG).show();

        initCamera(mCurrentLocation, 0);
        /*for(int count=0;count<friendlistlat.size();count++)
        {
            LatLng friend1 = new LatLng(friendlistlat.get(count),friendlistlong.get(count));
            mMap.addMarker(new MarkerOptions().position(friend1).title("Friend "+(count+1)));
        }*/

/*        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMaps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(ActivityMaps.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                initCamera(mCurrentLocation, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });*/

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
