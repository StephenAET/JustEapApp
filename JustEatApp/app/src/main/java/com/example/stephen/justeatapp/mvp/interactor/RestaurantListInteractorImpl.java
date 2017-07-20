package com.example.stephen.justeatapp.mvp.interactor;

import com.example.stephen.justeatapp.constant.Constants;
import com.example.stephen.justeatapp.model.RestaurantList;
import com.example.stephen.justeatapp.service.RestaurantAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantListInteractorImpl implements IRestaurantListInteractor {

    private RestaurantAPI restaurantAPI;

    public RestaurantListInteractorImpl(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)//Pass the Base URL here for making requests
                .addConverterFactory(GsonConverterFactory.create())//Create and Pass the Converter Factory for interpreting the Request Data
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Create and Pass the Call Adapter Factory for
                .client(okHttpClient)
                .build();

        restaurantAPI = retrofit.create(RestaurantAPI.class);
    }

    @Override
    public Observable<RestaurantList> getRestaurantList(String location) {
        return restaurantAPI.getRestaurantList(location);
    }
}
