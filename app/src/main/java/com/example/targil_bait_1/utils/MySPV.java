package com.example.targil_bait_1.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySPV
{
    private static final String DB_FILE = "DB_FILE";
    private static MySPV mySPV = null;
    private SharedPreferences preferences;

    public static MySPV getInstance()
    {
        return mySPV;
    }
    public static void init(Context context){
        if(mySPV == null)
        {
            mySPV= new MySPV(context);
        }

    }

    private MySPV(Context context)
    {
        preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
    }

    public String getStrSP(String key,String def)
    {
        return preferences.getString(key, def);
    }

    public void putString(String key,String value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
}
