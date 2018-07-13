package com.reweyou.master.kavyanjali.data.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;

import java.util.HashMap;

/**
 * Created by master on 1/4/18.
 */

public class ApiHelper {

    private static final String TAG = ApiHelper.class.getName();
    private static final String DEVICE_TYPE = "device_type";
    private static final String ANDROID = "android";
    private static final String PER = "10";
    private static final String BASE_URL = "";
    private final HashMap<String, String> headers;

    public ApiHelper(String keyToken) {
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + keyToken);
    }

    public void updateToken(String token) {
        headers.put("Authorization", "Bearer " + token);
    }


    public ANRequest loadsearchfeeds(String searchTag) {
        return AndroidNetworking.get("https://www.reweyou.in/poem/fetchPoems.php")
                .addHeaders(headers)
                .addQueryParameter("term", searchTag)
                .setPriority(Priority.HIGH)
                .build();
    }


    public ANRequest loadData() {
        return AndroidNetworking.get("https://www.reweyou.in/poem/fetchPoems.php")
                .setPriority(Priority.HIGH)
                .build();
    }

    public ANRequest fetchBackgroundData() {
        return AndroidNetworking.get("https://www.reweyou.in/poem/fetchbackground.php")
                .setPriority(Priority.HIGH)
                .build();
    }
}
