package com.example.stephen.justeatapp.mvp.presenter;

import com.example.stephen.justeatapp.mvp.view.IRestaurantListView;
import com.example.stephen.justeatapp.mvp.MvpPresenter;

/**
 * Mvp Managed Presenter for managing Restaurant List Views
 */
public interface IRestaurantListPresenter extends MvpPresenter<IRestaurantListView> {

    void performRestaurantList(String location);
}