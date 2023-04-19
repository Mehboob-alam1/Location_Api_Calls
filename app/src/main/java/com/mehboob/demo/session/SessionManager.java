package com.mehboob.demo.session;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {


    private SharedPreferences sharedPreferences;
    private static final String IP = "IP_ADDRESS";
    private static final String COUNTRY_NAME = "COUNTRY_NAME";
    private static final String COUNTRY_CODE = "COUNTRY_CODE";
    private static final String IS_SECOND = "IS_SECOND";
    private static final String IS_RATED = "IS_RATED";


    public SessionManager(Context context) {

        sharedPreferences = context.getSharedPreferences(IP, MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(COUNTRY_NAME, MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(COUNTRY_CODE, MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(IS_SECOND, MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(IS_RATED, MODE_PRIVATE);
    }


    public void saveIsRated(boolean isRated) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_RATED, isRated);
        editor.apply();
    }

    public boolean fetchIsRated() {

        return sharedPreferences.getBoolean(IS_RATED, false);
    }

    public void saveIsSecond(boolean isSecond) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SECOND, isSecond);
        editor.apply();
    }


    public boolean fetchIsSecond() {
        return sharedPreferences.getBoolean(IS_SECOND, false);
    }

    public void saveCountryName(String countryName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY_NAME, countryName);
        editor.apply();
    }

    public String fetchCountryName() {
        return sharedPreferences.getString(COUNTRY_NAME, null);
    }

    public void saveCountryCode(String countryCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY_CODE, countryCode);
        editor.apply();
    }

    public String fetchCountryCode() {
        return sharedPreferences.getString(COUNTRY_CODE, null);
    }

    public void saveIp(String ipAddress) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP, ipAddress);
        editor.apply();


    }

    public String fetchIP() {

        return sharedPreferences.getString(IP, null);
    }


}

