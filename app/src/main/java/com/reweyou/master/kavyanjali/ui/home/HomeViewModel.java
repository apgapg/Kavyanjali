package com.reweyou.master.kavyanjali.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.reweyou.master.kavyanjali.BR;
import com.reweyou.master.kavyanjali.data.AppDataManager;
import com.reweyou.master.kavyanjali.ui.base.BaseViewModel;

/**
 * Created by master on 12/7/18.
 */
public class HomeViewModel extends BaseViewModel {
    MutableLiveData<String> sampletext;

    @Bindable
    public MutableLiveData<String> getSampletext() {
        if (sampletext == null) {
            sampletext = new MutableLiveData<String>();
            sampletext.setValue("ssssssaaaaa");
            loadData();
        }
        return sampletext;
    }

    private void loadData() {
        AppDataManager.getInstance().getmApiHelper().loadData().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                sampletext.setValue(response);
                notifyPropertyChanged(BR.sampletext);
            }

            @Override
            public void onError(ANError anError) {
                sampletext.setValue(anError.getErrorBody());
            }
        });
    }


}
