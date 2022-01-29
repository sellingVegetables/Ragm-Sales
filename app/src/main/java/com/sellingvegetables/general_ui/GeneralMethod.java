package com.sellingvegetables.general_ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.util.Log;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sellingvegetables.R;
import com.sellingvegetables.tags.Tags;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter({"image"})
    public static void image(View view, byte[] endPoint) {
        if(endPoint!=null) {
            Log.e("sizess",endPoint.length+"");
            Bitmap bmp = BitmapFactory.decodeByteArray(endPoint, 0, endPoint.length);

            if (view instanceof CircleImageView) {
                CircleImageView imageView = (CircleImageView) view;
                if (endPoint != null) {

                    imageView.setImageBitmap(bmp);
                }
            } else if (view instanceof RoundedImageView) {
                RoundedImageView imageView = (RoundedImageView) view;

                if (endPoint != null) {

                    imageView.setImageBitmap(bmp);
                }
            } else if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;

                if (endPoint != null) {

                    imageView.setImageBitmap(bmp);
                }
            }
        }
    }


}










