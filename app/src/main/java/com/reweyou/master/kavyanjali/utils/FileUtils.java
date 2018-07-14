package com.reweyou.master.kavyanjali.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by master on 14/7/18.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getName();
    private static final String APP_DIRECTORY_NAME = "Kavyanjali";

    public static String getPublicAlbumStorageDir(Context context, Bitmap bitmap) {
        String directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File file1 = new File(directory + "/" + APP_DIRECTORY_NAME);
        file1.mkdirs();
        if (!file1.exists()) {
            throw new RuntimeException("Directory not created");
        }

        int n = 10000;
        Random generator = new Random();

        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File imageFile = new File(file1, fname);
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();


            MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);


                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return imageFile.getAbsolutePath();

    }
}
