package com.reweyou.master.kavyanjali;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.reweyou.master.kavyanjali.data.AppDataManager;

/**
 * Created by master on 11/7/18.
 */
public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        AppDataManager.initialize(getApplicationContext());
    }
}
