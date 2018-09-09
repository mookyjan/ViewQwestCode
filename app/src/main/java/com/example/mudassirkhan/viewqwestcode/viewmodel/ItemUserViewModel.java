package com.example.mudassirkhan.viewqwestcode.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import com.example.mudassirkhan.viewqwestcode.R;
import com.example.mudassirkhan.viewqwestcode.model.User;
import com.bumptech.glide.Glide;

public class ItemUserViewModel extends BaseObservable {

    private User user;
    private Context context;

    public ItemUserViewModel(User user, Context context) {
        this.user = user;
        this.context = context;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null)
            url = url.replace("http", "https");
        //set placeholder and error image also
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error_image))
                .into(imageView);
    }

    public String getUserName() {
        return user.name;
    }

    public String getUserImage() {
        return user.image;
    }

    public void setUser(User user) {
        this.user = user;
        notifyChange();
    }
}
