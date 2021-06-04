package com.example.mcostudentmovementconfirmation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

public class preferences {
    private static final String DATA_LOGIN = "status_login",
    DATA_STATUS = "studentID";

    private static SharedPreferences getSharedReferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataStatus(Context context, String data)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_STATUS,data);
        editor.apply();
    }

    public static String getDataStatus(Context context)
    {
        return getSharedReferences(context).getString(DATA_STATUS,"");
    }

    public static void setDataLogin(Context context, boolean status)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN,status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context)
    {
        return getSharedReferences(context).getBoolean(DATA_LOGIN,false);
    }

    public static void clearData(Context context)
    {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_STATUS);
        editor.remove(DATA_LOGIN);
        editor.apply();
    }
}
