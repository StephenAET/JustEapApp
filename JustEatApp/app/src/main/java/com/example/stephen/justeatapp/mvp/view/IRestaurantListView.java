package com.example.stephen.justeatapp.mvp.view;


import com.example.stephen.justeatapp.model.RestaurantList;
import com.example.stephen.justeatapp.mvp.MvpView;

/**
 * Mvp Managed View for Displaying RestaurantList Data
 */
public interface IRestaurantListView extends MvpView {

    void onFetchDataSuccess(RestaurantList restaurantList);
    void onFetchDataError(Throwable throwable);
    void onFetchDataNoConnection();
}
