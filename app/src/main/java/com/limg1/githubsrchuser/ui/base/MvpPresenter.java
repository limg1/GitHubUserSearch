package com.limg1.githubsrchuser.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}

