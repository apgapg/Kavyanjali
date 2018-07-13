package com.reweyou.master.kavyanjali.ui.image;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.reweyou.master.kavyanjali.GlideApp;
import com.reweyou.master.kavyanjali.R;
import com.reweyou.master.kavyanjali.adapter.BackgroundAdapter;
import com.reweyou.master.kavyanjali.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity implements ItemBackgroundClickListener {

    private static final String TAG = ImageActivity.class.getName();
    private static final int TEXT_PADDING = 80;
    private int mTextGravity = 0;
    private int mTextSize = 18;
    private int MIN_TEXT_SIZE = 14;
    private int MAX_TEXT_SIZE = 20;
    private int MIN_IMAGE_HEIGHT = 300;
    private double IMAGE_ASPECT_RATIO = 0.7;
    private int mTypefaceIndex = 0;
    private BackgroundAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageViewModel mModel;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mModel = ViewModelProviders.of(this).get(ImageViewModel.class);

        imageView = findViewById(R.id.imageView);
        final ImageView imageViewdisplay = findViewById(R.id.imageView1);
        final View container = findViewById(R.id.container);
        final TextView textView = findViewById(R.id.textview);

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mAdapter = new BackgroundAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        mModel.getBackgroundData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mAdapter.add(strings);
            }
        });


        textView.setText("वहम से भी अक्सर खत्म हो जाते हैं कुछ रिश्ते\n" +
                "कसूर हर बार गल्तियों का नही होता ");


        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (textView.getViewTreeObserver().isAlive())
                    textView.getViewTreeObserver().removeOnPreDrawListener(this);

                Log.d(TAG, "onPreDraw: " + textView.getHeight());
                int height = textView.getHeight();
                int width = textView.getWidth();

                if (Utils.pxToDp(getApplicationContext(), height) < IMAGE_ASPECT_RATIO * width)
                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (IMAGE_ASPECT_RATIO * width)));
                else
                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
                GlideApp.with(ImageActivity.this).load(getResources().getDrawable(R.drawable.b)).into(imageView);

                return false;
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = loadBitmapFromView(findViewById(R.id.container), container.getWidth(), container.getHeight());
                GlideApp.with(ImageActivity.this).load(bitmap).into(imageViewdisplay);


            }
        });

        findViewById(R.id.toolbar_size).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextSize = mTextSize + 2;
                if (mTextSize > MAX_TEXT_SIZE)
                    mTextSize = MIN_TEXT_SIZE;
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
            }
        });


        final List<Typeface> list = new ArrayList<>();
        list.add(ResourcesCompat.getFont(ImageActivity.this, R.font.kalam_regular));
        list.add(ResourcesCompat.getFont(ImageActivity.this, R.font.khula_semi_bold));
        list.add(ResourcesCompat.getFont(ImageActivity.this, R.font.poppins_medium));
        list.add(ResourcesCompat.getFont(ImageActivity.this, R.font.yantramanav_medium));

        findViewById(R.id.toolbar_align).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mTextGravity) {
                    case -1:
                        mTextGravity = 0;
                        textView.setGravity(Gravity.CENTER);
                        break;
                    case 0:
                        mTextGravity = 1;
                        textView.setGravity(Gravity.RIGHT);
                        break;
                    case 1:
                        mTextGravity = -1;
                        textView.setGravity(Gravity.LEFT);
                        break;

                }

            }
        });

        findViewById(R.id.toolbar_font).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTypefaceIndex++;
                if (mTypefaceIndex > list.size() - 1)
                    mTypefaceIndex = 0;
                textView.setTypeface(list.get(mTypefaceIndex));
            }
        });


    }

    public Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @Override
    public void onItemBackgoundClick(String backgroundUrl) {
        GlideApp.with(ImageActivity.this).load(backgroundUrl).into(imageView);
    }
}


