package com.example.stephen.justeatapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.stephen.justeatapp.constant.Constants;
import com.example.stephen.justeatapp.model.Restaurant;
import com.example.stephen.justeatapp.model.RestaurantList;
import com.example.stephen.justeatapp.mvp.presenter.IRestaurantListPresenter;
import com.example.stephen.justeatapp.mvp.view.IRestaurantListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

public class MapsActivity extends FragmentActivity implements IRestaurantListView, OnMapReadyCallback {

    private GoogleMap mMap;

    private RestaurantList restaurantList;

    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;

    private IRestaurantListPresenter restaurantListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ((App)getApplication()).getPresenterComponent().inject(this);

        //Attach this Activity's View
        restaurantListPresenter.attachView(this);

        //Fetch Restaurant List
        restaurantListPresenter.performRestaurantList(Constants.VALUE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Inject
    public void setRestaurantListPresenter(IRestaurantListPresenter restaurantListPresenter) {
        this.restaurantListPresenter = restaurantListPresenter;
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
        mMap = googleMap;

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            //TODO
        }

        try {
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));


        } catch (Resources.NotFoundException e){
            Log.e("Error", "Can't find style. Error: ", e);
        }


        checkFineLocationPermission();


        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                } else {
                    //Damn
                }
                return;
            }
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);


    }

    private void checkFineLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onFetchDataSuccess(RestaurantList restaurantList) {
        this.restaurantList = restaurantList;


        if (restaurantList != null) {

            for(Restaurant restaurant : restaurantList.getRestaurants()){
                addMarkerForRestaurant(restaurant, mMap);
            }

            Restaurant lastRestaurant = restaurantList.getRestaurants().get(restaurantList.getRestaurants().size() - 1);
            LatLng latLng = new LatLng(lastRestaurant.getLatitude(), lastRestaurant.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private void addMarkerForRestaurant(Restaurant restaurant, GoogleMap map) {

        String title = restaurant.getName();

        Double latitude = restaurant.getLatitude();
        Double longitude = restaurant.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        map.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    @Override
    public void onFetchDataError(Throwable throwable) {

    }

    @Override
    public void onFetchDataNoConnection() {

    }
}
