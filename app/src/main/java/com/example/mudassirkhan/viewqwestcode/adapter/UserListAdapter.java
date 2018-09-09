package com.example.mudassirkhan.viewqwestcode.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.mudassirkhan.viewqwestcode.R;
import com.example.mudassirkhan.viewqwestcode.databinding.SingleItemUserLayoutBinding;
import com.example.mudassirkhan.viewqwestcode.model.User;
import com.example.mudassirkhan.viewqwestcode.viewmodel.ItemUserViewModel;
import java.util.Collections;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserAdapterViewHolder> {

    private List<User> userList;
    private SingleItemUserLayoutBinding singleItemUserLayoutBinding;

    public UserListAdapter() {
        this.userList = Collections.emptyList();
    }

    @Override
    public UserAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        singleItemUserLayoutBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_item_user_layout,
                        parent, false);

        return new UserAdapterViewHolder(singleItemUserLayoutBinding);
    }

    @Override
    public void onBindViewHolder(UserAdapterViewHolder holder, int position) {
        holder.bindUser(userList.get(position));
        setupImageList(holder.singleItemUserLayoutBinding.itemRecyclerView, userList.get(position).items);
    }

    private void setupImageList(RecyclerView recyclerView, final List<String> imagesList) {
        ImageListAdapter adapter = new ImageListAdapter();
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (imagesList != null && (imagesList.size() % 2) != 0 && position == 0) ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setImagesList(imagesList);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public static class UserAdapterViewHolder extends RecyclerView.ViewHolder {
        SingleItemUserLayoutBinding singleItemUserLayoutBinding;

        public UserAdapterViewHolder(SingleItemUserLayoutBinding singleItemUserLayoutBinding) {
            super(singleItemUserLayoutBinding.itemUser);
            this.singleItemUserLayoutBinding = singleItemUserLayoutBinding;
        }

        void bindUser(User user) {
            if (singleItemUserLayoutBinding.getUserViewModel() == null) {
                singleItemUserLayoutBinding.setUserViewModel(
                        new ItemUserViewModel(user, itemView.getContext()));
            } else {
                singleItemUserLayoutBinding.getUserViewModel().setUser(user);
            }
        }
    }
}
