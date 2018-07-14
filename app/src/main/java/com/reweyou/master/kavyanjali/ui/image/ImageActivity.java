package com.reweyou.master.kavyanjali.ui.image;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.reweyou.master.kavyanjali.GlideApp;
import com.reweyou.master.kavyanjali.R;
import com.reweyou.master.kavyanjali.adapter.BackgroundAdapter;
import com.reweyou.master.kavyanjali.utils.FileUtils;
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
    private TextView textView;
    private boolean isCopyright = false;
    private ScrollView mScrollView;
    private View container;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mModel = ViewModelProviders.of(this).get(ImageViewModel.class);

        content = getIntent().getStringExtra("content");
        if (content.length() > 300)
            mTextSize = 16;
        else if (content.length() > 500)
            mTextSize = 14;


        imageView = findViewById(R.id.image);
        container = findViewById(R.id.container);
        textView = findViewById(R.id.textview);
        mScrollView = findViewById(R.id.scrollView);
        mRecyclerView = findViewById(R.id.recycler_view);


        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mAdapter = new BackgroundAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        mModel.getBackgroundData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mAdapter.add(strings);
            }
        });


        textView.setText(content);


        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (textView.getViewTreeObserver().isAlive())
                    textView.getViewTreeObserver().removeOnPreDrawListener(this);

                Log.d(TAG, "onPreDraw: " + textView.getHeight());
                int height = textView.getHeight();
                int width = textView.getWidth();

                RelativeLayout.LayoutParams layoutParams;
                if (height < IMAGE_ASPECT_RATIO * width)
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (IMAGE_ASPECT_RATIO * width));
                else
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + Utils.dpToPx(getApplicationContext(), 40));

                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                imageView.setLayoutParams(layoutParams);
                GlideApp.with(ImageActivity.this).load(getResources().getDrawable(R.drawable.b)).into(imageView);

                return false;
            }
        });


        findViewById(R.id.toolbar_size).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextHeightChange();

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
                onTextHeightChange();
                mTypefaceIndex++;
                if (mTypefaceIndex > list.size() - 1)
                    mTypefaceIndex = 0;
                textView.setTypeface(list.get(mTypefaceIndex));
            }
        });

        findViewById(R.id.toolbar_copyright).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onTextHeightChange();


                textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        if (textView.getViewTreeObserver().isAlive())
                            textView.getViewTreeObserver().removeOnPreDrawListener(this);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mScrollView.fullScroll(View.FOCUS_DOWN);

                            }
                        }, 100);

                        return false;
                    }
                });


                String text;
                if (!isCopyright) {
                    isCopyright = true;
                    text = textView.getText().toString().concat("\n— Ayush P Gupta");
                } else {
                    isCopyright = false;
                    text = textView.getText().toString().replace("\n— Ayush P Gupta", "");
                }

                textView.setText(text);
            }
        });

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = loadBitmapFromView(container, container.getWidth(), container.getHeight());
                String imagePath = FileUtils.getPublicAlbumStorageDir(ImageActivity.this, bitmap);
                bitmap.recycle();

                Intent i = new Intent(ImageActivity.this, UploadImageActivity.class);
                i.putExtra("content", textView.getText().toString());
                i.putExtra("imagepath", imagePath);
                startActivity(i);
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

    public void onTextHeightChange() {

        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (textView.getViewTreeObserver().isAlive())
                    textView.getViewTreeObserver().removeOnPreDrawListener(this);

                Log.d(TAG, "onPreDraw: " + textView.getHeight());
                int height = textView.getHeight();
                int width = textView.getWidth();

                RelativeLayout.LayoutParams layoutParams;
                if (height < IMAGE_ASPECT_RATIO * width)
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (IMAGE_ASPECT_RATIO * width));
                else
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + Utils.dpToPx(getApplicationContext(), 40));

                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                imageView.setLayoutParams(layoutParams);

                return false;
            }
        });
    }
}


