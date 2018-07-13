package com.reweyou.master.kavyanjali.ui.base;

public interface Presentor<T extends MvpView> {

    void attachView(T mvpView);
    void detachView();
}
