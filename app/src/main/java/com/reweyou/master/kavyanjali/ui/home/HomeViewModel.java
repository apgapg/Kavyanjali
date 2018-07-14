package com.reweyou.master.kavyanjali.ui.home;

import android.arch.lifecycle.MutableLiveData;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.reweyou.master.kavyanjali.data.AppDataManager;
import com.reweyou.master.kavyanjali.data.model.PoemModel;
import com.reweyou.master.kavyanjali.ui.base.BaseViewModel;
import com.reweyou.master.kavyanjali.utils.NetworkUtils;

import java.util.List;

/**
 * Created by master on 12/7/18.
 */
public class HomeViewModel extends BaseViewModel {
    private static final String TAG = HomeViewModel.class.getName();
    MutableLiveData<Boolean> isLoading;
    MutableLiveData<List<PoemModel>> poemList;


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
            isLoading.setValue(false);
        }
        return isLoading;
    }

    public MutableLiveData<List<PoemModel>> getPoemList() {
        if (poemList == null) {
            poemList = new MutableLiveData<>();
            loadData();
        }
        return poemList;
    }

    private void loadData() {
        AppDataManager.getInstance().getmApiHelper().loadData().getAsObjectList(PoemModel.class, new ParsedRequestListener<List<PoemModel>>() {


            @Override
            public void onResponse(List<PoemModel> response) {
                NetworkUtils.parseResponse(TAG, response);
                poemList.setValue(response);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
            }
        });
    }


}
