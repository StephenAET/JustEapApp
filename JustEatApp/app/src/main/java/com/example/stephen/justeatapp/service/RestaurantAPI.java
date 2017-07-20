package com.example.stephen.justeatapp.service;

import com.example.stephen.justeatapp.constant.Constants;
import com.example.stephen.justeatapp.model.RestaurantList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestaurantAPI {

    @Headers({
            Constants.HEADER_ACCEPT_TENANT,
            Constants.HEADER_ACCEPT_LANGUAGE,
            Constants.HEADER_AUTHORIZATION,
            Constants.HEADER_HOST
    })
    @GET(Constants.BASE_URL)
    Observable<RestaurantList> getRestaurantList(@Query(Constants.QUERY) String location);
}
