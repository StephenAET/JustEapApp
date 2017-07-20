package com.example.stephen.justeatapp.mvp.presenter;

import android.util.Log;

import com.example.stephen.justeatapp.model.RestaurantList;
import com.example.stephen.justeatapp.mvp.interactor.IRestaurantListInteractor;
import com.example.stephen.justeatapp.mvp.interactor.RestaurantListInteractorImpl;
import com.example.stephen.justeatapp.mvp.view.IRestaurantListView;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RestaurantListPresenterImpl implements IRestaurantListPresenter{

    private IRestaurantListView restaurantListView;
    private IRestaurantListInteractor restaurantListInteractor;

    public RestaurantListPresenterImpl(){
        this.restaurantListInteractor = new RestaurantListInteractorImpl();
    }

    @Override
    public void attachView(IRestaurantListView mvpView) {
        this.restaurantListView = mvpView;
    }

    @Override
    public void performRestaurantList(String location) {

        ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            restaurantListInteractor.getRestaurantList(location)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.newThread())
                                    .subscribe(this::onSuccess, this::onError);
                        } else {
                            restaurantListView.onFetchDataNoConnection();
                        }
                    }

                    private void onError(Throwable throwable) {
                        restaurantListView.onFetchDataError(throwable);
                        Log.e("Whoops", throwable.getLocalizedMessage());
                    }

                    private void onSuccess(RestaurantList restaurantList) {
                        restaurantListView.onFetchDataSuccess(restaurantList);
                        Log.i("Success","Yeah boi!");
                    }
                });
    }



}
