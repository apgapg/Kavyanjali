package com.reweyou.master.kavyanjali.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by master on 14/7/18.
 */
public class ToastUtils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
