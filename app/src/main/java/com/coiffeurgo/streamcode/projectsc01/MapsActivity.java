package com.coiffeurgo.streamcode.projectsc01;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.coiffeurgo.streamcode.projectsc01.controllers.CompaniesController;
import com.coiffeurgo.streamcode.projectsc01.models.Companies;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private List<Companies> _cps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
    public void onMapReady(GoogleMap googleMap) {
        final Intent i = getIntent();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        if(i.hasExtra("name")){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    CompaniesController _ctlCps = new CompaniesController();
                    _ctlCps.SearchWithNameCompanies(i.getStringExtra("name"));
                    while (_ctlCps.getAnswer()==null){}
                    if(_ctlCps.getAnswer().isStatus()){
                        _cps = _ctlCps.getAnswer().getCompanies();
                    }else{
                        String msg = "";
                        for (String error : _ctlCps.getAnswer().getErrors()) {
                            msg+= error.concat("\n");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }.start();
        }else{
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    CompaniesController _ctlCps = new CompaniesController();
                                    _ctlCps.SearchWithLocationBarberShops(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()),i.getStringExtra("distance").replace("K",""));
                                    while (_ctlCps.getAnswer()==null){}
                                    if(_ctlCps.getAnswer().isStatus()){
                                        _cps = _ctlCps.getAnswer().getCompanies();
                                    }else{
                                        String msg = "";
                                        for (String error : _ctlCps.getAnswer().getErrors()) {
                                            msg+= error.concat("\n");
                                        }
                                        Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                            }.start();
                        }
                    }
                });
        }

        this.buildMarkers();
    }

    public void buildMarkers(){
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                if(_cps==null){
                    buildMarkers();
                }else{
                    // Add a marker in Sydney and move the camera
                    //LatLng sydney = new LatLng(-34, 151);
                    //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                    for (int i = 0; i < _cps.size(); i++) {
                        LatLng tempPos = new LatLng(Double.parseDouble(_cps.get(i).getLatitude()), Double.parseDouble(_cps.get(i).getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(tempPos).title(_cps.get(i).getName()));
                        if(i==0){
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(tempPos));
                        }
                    }
                }
            }
        }, 1000);
    }
}
