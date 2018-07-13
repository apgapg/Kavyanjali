package com.reweyou.master.kavyanjali.utils;

import android.util.Log;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by master on 4/4/18.
 */

public class NetworkUtils {

    public static void parseError(String TAG, ANError error) {
        if (error.getErrorCode() != 0) {
            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
            // ApiError apiError = error.getErrorAsObject(ApiError.class);
        } else {
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
        }
    }

    public static void parseResponse(String TAG, Object response) {
        if (response instanceof String) {
            Log.d(TAG, "parseResponse: " + response);
        } else if (response instanceof JSONObject) {
            Log.d(TAG, "parseResponse: " + response.toString());
        } else if (response instanceof JSONArray) {
            Log.d(TAG, "parseResponse: " + response.toString());
        } else if (response instanceof List) {
            Log.d(TAG, "parseResponse: " + new Gson().toJson(response));
        }
    }
}
