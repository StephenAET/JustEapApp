package com.example.stephen.justeatapp.mvp;

public interface MvpPresenter <V extends MvpView>{

    //Ensure that One view has a single Presenter
    void attachView(V mvpView);
    //void detachView();
}
