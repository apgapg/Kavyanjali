package com.reweyou.master.kavyanjali.ui.image;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.reweyou.master.kavyanjali.R;
import com.reweyou.master.kavyanjali.utils.ToastUtils;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class WriteTextActivity extends AppCompatActivity {

    private EditText editText;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_text);
        editText = findViewById(R.id.editText);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                if ((clipboard.hasPrimaryClip()) && clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                    text = item.getText().toString();

                    editText.setText(text);
                    editText.setSelection(text.length() - 1);
                } else {
                    ToastUtils.showToast(WriteTextActivity.this, "No clip data available");
                }
            }


        });
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = editText.getText().toString();
                if (!content.isEmpty()) {
                    Intent i = new Intent(WriteTextActivity.this, ImageActivity.class);
                    i.putExtra("content", content);
                    startActivity(i);
                } else ToastUtils.showToast(WriteTextActivity.this, "Content cannot be empty!");
            }
        });
    }
}
