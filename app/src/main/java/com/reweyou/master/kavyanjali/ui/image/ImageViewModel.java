package com.reweyou.master.kavyanjali.ui.image;

import android.arch.lifecycle.MutableLiveData;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.reweyou.master.kavyanjali.data.AppDataManager;
import com.reweyou.master.kavyanjali.ui.base.BaseViewModel;
import com.reweyou.master.kavyanjali.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by master on 13/7/18.
 */
public class ImageViewModel extends BaseViewModel {
    private static final String TAG = ImageViewModel.class.getName();
    MutableLiveData<List<String>> backgroundList;

    public MutableLiveData<List<String>> getBackgroundData() {
        if (backgroundList == null)
            backgroundList = new MutableLiveData<>();
        backgroundList.setValue(new ArrayList<String>());
        fetchBackgroundData();
        return backgroundList;

    }

    private void fetchBackgroundData() {
        AppDataManager.getInstance().getmApiHelper().fetchBackgroundData().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                NetworkUtils.parseResponse(TAG, response);
                JSONArray namearray = response.names();
                List<String> list = new ArrayList<>();
                for (int i = 0; i < namearray.length(); i++) {
                    try {
                        list.add(response.getString(namearray.getString(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                backgroundList.setValue(list);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
            }
        });
    }


}
