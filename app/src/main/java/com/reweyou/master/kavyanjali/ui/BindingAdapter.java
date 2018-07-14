package com.reweyou.master.kavyanjali.ui;

import android.content.Context;
import android.widget.ImageView;

import com.reweyou.master.kavyanjali.GlideApp;

/**
 * Created by master on 14/7/18.
 */
public class BindingAdapter {
    @android.databinding.BindingAdapter("imageUrl")
    public static void loadImagefromUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (url != null)
            GlideApp.with(context).load(url).thumbnail(0.1f).into(imageView);

    }
}
