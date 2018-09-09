package com.example.mudassirkhan.viewqwestcode.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.mudassirkhan.viewqwestcode.R;
import com.example.mudassirkhan.viewqwestcode.databinding.SingleItemImageLayoutBinding;
import com.example.mudassirkhan.viewqwestcode.viewmodel.ItemImageViewModel;
import java.util.Collections;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private List<String> imagesList;
    private SingleItemImageLayoutBinding singleItemImageLayoutBinding;

    public ImageListAdapter() {
        this.imagesList = Collections.emptyList();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        singleItemImageLayoutBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_item_image_layout,
                        parent, false);

        return new ImageViewHolder(singleItemImageLayoutBinding);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bindItem(imagesList.get(position));

        if (position == 0 && imagesList != null && imagesList.size() % 2 != 0) {
            final ViewGroup.LayoutParams layoutParams = holder.singleItemImageLayoutBinding.getRoot().getLayoutParams();
            GridLayoutManager layoutManager = new GridLayoutManager(singleItemImageLayoutBinding.getRoot().getContext(), 2);
            // Set the height by params
            layoutParams.height = holder.singleItemImageLayoutBinding.getRoot().getLayoutParams().height * 2;
            // set height of RecyclerView
            holder.singleItemImageLayoutBinding.getRoot().setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        SingleItemImageLayoutBinding singleItemImageLayoutBinding;

        public ImageViewHolder(SingleItemImageLayoutBinding singleItemImageLayoutBinding) {
            super(singleItemImageLayoutBinding.itemImage);
            this.singleItemImageLayoutBinding = singleItemImageLayoutBinding;
        }

        void bindItem(String imageUrl) {
            if (singleItemImageLayoutBinding.getImageViewModel() == null) {
                singleItemImageLayoutBinding.setImageViewModel(
                        new ItemImageViewModel(imageUrl, itemView.getContext()));
            } else {
                singleItemImageLayoutBinding.getImageViewModel().setImageUrl(imageUrl);
            }
        }
    }
}

