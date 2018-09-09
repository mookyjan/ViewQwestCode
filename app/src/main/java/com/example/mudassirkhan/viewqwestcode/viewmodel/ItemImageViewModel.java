package com.example.mudassirkhan.viewqwestcode.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class ItemImageViewModel extends BaseObservable {

    private String imageUrl;
    private Context context;

    public ItemImageViewModel(String imageUrl, Context context) {
        this.imageUrl = imageUrl;
        this.context = context;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null)
            url = url.replace("http", "https");
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyChange();
    }
}
