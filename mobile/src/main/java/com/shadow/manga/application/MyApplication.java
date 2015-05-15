package com.shadow.manga.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shane on 5/14/2015.
 */
public class MyApplication extends Application {

    public static MyApplication sInstance = null;

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
