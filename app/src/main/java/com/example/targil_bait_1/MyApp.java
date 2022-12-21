package com.example.targil_bait_1;

import android.app.Application;

import com.example.targil_bait_1.utils.MySPV;
import com.example.targil_bait_1.utils.MySignal;

public class MyApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        MySignal.init(this);
        MySPV.init(this);
    }
}
