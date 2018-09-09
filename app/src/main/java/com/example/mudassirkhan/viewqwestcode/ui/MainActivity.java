package com.example.mudassirkhan.viewqwestcode.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.mudassirkhan.viewqwestcode.R;
import com.example.mudassirkhan.viewqwestcode.adapter.UserListAdapter;
import com.example.mudassirkhan.viewqwestcode.databinding.ActivityMainBinding;
import com.example.mudassirkhan.viewqwestcode.utils.EndlessRecyclerOnScrollListener;
import com.example.mudassirkhan.viewqwestcode.viewmodel.MainViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        populateRecyclerView(activityMainBinding.userRecyclerView);
        setupObserver(mainViewModel);
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);
    }

    private void populateRecyclerView(RecyclerView userRecyclerView) {
        UserListAdapter adapter = new UserListAdapter();
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        userRecyclerView.setAdapter(adapter);
        userRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (mainViewModel.hasMore)
                    addDataToList();
            }
        });
    }

    private void addDataToList() {
        mainViewModel.getUserListFromAPI();
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            UserListAdapter userListAdapter = (UserListAdapter) activityMainBinding.userRecyclerView.getAdapter();
            MainViewModel mainViewModel = (MainViewModel) observable;
            userListAdapter.setUserList(mainViewModel.getUserList());
        }
    }
}
