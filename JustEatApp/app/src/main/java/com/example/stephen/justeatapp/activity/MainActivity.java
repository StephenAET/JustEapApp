package com.example.stephen.justeatapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.stephen.justeatapp.R;
import com.example.stephen.justeatapp.constant.Constants;
import com.example.stephen.justeatapp.model.Restaurant;
import com.example.stephen.justeatapp.model.RestaurantList;
import com.example.stephen.justeatapp.mvp.presenter.IRestaurantListPresenter;
import com.example.stephen.justeatapp.mvp.presenter.RestaurantListPresenterImpl;
import com.example.stephen.justeatapp.mvp.view.IRestaurantListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements IRestaurantListView, OnMapReadyCallback {

    IRestaurantListPresenter restaurantListPresenter;

    private GoogleMap mMap;

    RestaurantList restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove when using Dagger
        setRestaurantListPresenter(new RestaurantListPresenterImpl());

        //Attach this Activity's View
        restaurantListPresenter.attachView(this);

        //Fetch Restaurant List
        restaurantListPresenter.performRestaurantList(Constants.VALUE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onFetchDataSuccess(RestaurantList restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public void onFetchDataError(Throwable throwable) {
        Log.e("Whoops", throwable.getLocalizedMessage());
    }

    @Override
    public void onFetchDataNoConnection() {
        Log.e("Whoops", "Cracker you have no internet!");
    }

    //Inject Here
    public void setRestaurantListPresenter(IRestaurantListPresenter restaurantListPresenter) {
        this.restaurantListPresenter = restaurantListPresenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.i("MapLoad","Loaded");
        mMap = googleMap;

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e){
            //TODO
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //try {
        //    mMap.setMyLocationEnabled(true);
        //} catch (SecurityException e){
        //    //TODO
        //}

        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        //if (restaurantList != null){
        //    restaurantList.getRestaurants()
        //            .forEach(restaurant -> addMarkerForRestaurant(restaurant, mMap));
        //}
    }

    private void addMarkerForRestaurant(Restaurant restaurant, GoogleMap map){

        String title = restaurant.getName();

        Double latitude = restaurant.getLatitude();
        Double longitude = restaurant.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

        map.addMarker(new MarkerOptions().position(latLng).title(title));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
