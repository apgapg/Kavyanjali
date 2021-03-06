/*
package com.reweyou.master.kavyanjali.Utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.farm.farm2fork.GlideApp;

public class GlideUtils {

    public static void loadImagefromUrl(Context mContext, String url, ImageView imageView) {
        GlideApp.with(mContext).load(new CustomGlideUrl(url)).thumbnail(0.1f).into(imageView);

    }

    @BindingAdapter("imageUrl")
    public static void loadImagefromUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (url != null)
            GlideApp.with(context).load(new CustomGlideUrl(url)).thumbnail(0.1f).into(imageView);

    }

    public static void loadImagefromUri(Context mContext, Uri uri, ImageView imageView) {
        GlideApp.with(mContext).load(uri).into(imageView);

    }

    public static class CustomGlideUrl extends GlideUrl {

        public CustomGlideUrl(String url) {
            super(url);
        }

        @Override
        public String getCacheKey() {
            String url = toStringUrl();
            if (url.contains("?")) {
                return url.substring(0, url.lastIndexOf("?"));

            } else
                return url;
        }
    }
}
*/
