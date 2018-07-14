package com.reweyou.master.kavyanjali.ui.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.reweyou.master.kavyanjali.GlideApp;
import com.reweyou.master.kavyanjali.R;
import com.reweyou.master.kavyanjali.data.AppDataManager;
import com.reweyou.master.kavyanjali.utils.MyProgressDialog;
import com.reweyou.master.kavyanjali.utils.NetworkUtils;
import com.reweyou.master.kavyanjali.utils.ToastUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UploadImageActivity extends AppCompatActivity {

    private EditText editText;
    private String content;
    private String TAG = UploadImageActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        editText = findViewById(R.id.editText);

        content = getIntent().getStringExtra("content");
        final String imagePath = getIntent().getStringExtra("imagepath");


        ImageView imageView = findViewById(R.id.imageView4);
        GlideApp.with(this).load(imagePath).into(imageView);


        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File file = new File(imagePath);
                if (!file.exists())
                    throw new NullPointerException("Image File does not exist");

                int size = (int) file.length();
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();

                    onRead(bytes);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });


    }

    private void onRead(byte[] bytes) {

        MyProgressDialog.showProgressDialog(UploadImageActivity.this, "Uploading! Please Wait.");
        String imageencoded = Base64.encodeToString(bytes, Base64.DEFAULT);

        if (!editText.getText().toString().isEmpty() && imageencoded != null && !imageencoded.isEmpty())
            AppDataManager.getInstance().getmApiHelper().uploadPost(content, editText.getText().toString(), imageencoded).getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    NetworkUtils.parseResponse(TAG, response);
                    MyProgressDialog.dismissProgressDialog();
                    ToastUtils.showToast(UploadImageActivity.this, response);
                }

                @Override
                public void onError(ANError anError) {
                    NetworkUtils.parseResponse(TAG, anError.getResponse());
                    MyProgressDialog.dismissProgressDialog();
                }
            });
    }
}
