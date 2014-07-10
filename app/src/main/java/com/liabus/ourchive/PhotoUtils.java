package com.liabus.ourchive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;

/**
 * Created by jordangens on 7/6/14.
 */
public class PhotoUtils {
    public static Bitmap getThumbnailFromUrl(String path, int max){
        Bitmap myBitmap = BitmapFactory.decodeFile(path);
        myBitmap = scaleBitmap(myBitmap, max);
        return myBitmap;
    }

    private static Bitmap scaleBitmap(Bitmap bm, int max) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        if (width > height) {
            width = (int)(((double)width / (double)height) * max);
            height = max;
        } else if (height > width) {
            height = (int)(((double)height / (double)width) * max);
            width = max;
        } else {
            // square
            height = max;
            width = max;
        }

        bm = Bitmap.createScaledBitmap(bm, width, height, true);

        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return bm;
    }

    public static int dpToPx(DisplayMetrics displayMetrics, int dp) {
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
