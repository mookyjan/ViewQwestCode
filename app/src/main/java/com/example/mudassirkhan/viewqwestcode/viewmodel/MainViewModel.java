package com.example.mudassirkhan.viewqwestcode.viewmodel;

import android.content.Context;
import android.content.IntentFilter;
import android.databinding.ObservableInt;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.mudassirkhan.viewqwestcode.ViewQwestApplication;
import com.example.mudassirkhan.viewqwestcode.api.ViewQwestServiceProvider;
import com.example.mudassirkhan.viewqwestcode.api.userList.UserListService;
import com.example.mudassirkhan.viewqwestcode.model.User;
import com.example.mudassirkhan.viewqwestcode.model.UserListResponse;
import com.example.mudassirkhan.viewqwestcode.utils.NetworkChangeReceiver;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainViewModel extends Observable implements NetworkChangeReceiver.NetworkStateReceiverListener {

    public ObservableInt progressBarVisibility;
    public ObservableInt recyclerViewVisibility;
    public ObservableInt noInternetVisibility;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    public boolean hasMore = true;
    public int offset = 10;
    public int limit = 10;
    private List<User> userList;
    private Context context;
    private NetworkChangeReceiver networkChangeReceiver;

    public MainViewModel(@NonNull Context context) {
        this.context = context;
        this.userList = new ArrayList<>();
        progressBarVisibility = new ObservableInt(View.GONE);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        noInternetVisibility = new ObservableInt(View.GONE);

        // Add Network Listener
        networkChangeReceiver = new NetworkChangeReceiver();
        networkChangeReceiver.addListener(this);
        context.registerReceiver(networkChangeReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        initializeViews();
        getUserListFromAPI();
    }

    public void initializeViews() {
        recyclerViewVisibility.set(View.GONE);
    }

    public void getUserListFromAPI() {
        if (isNetworkAvailable()) {
            progressBarVisibility.set(View.VISIBLE);
            noInternetVisibility.set(View.GONE);

            ViewQwestApplication viewQwestApplication = ViewQwestApplication.create(context);
            UserListService userListService=ViewQwestServiceProvider.get().getUserList();
            Disposable disposable = userListService.getUserList( offset , limit)
                    .subscribeOn(viewQwestApplication.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UserListResponse>() {
                        @Override
                        public void accept(UserListResponse userResponse) throws Exception {
                            changeUserDataSet(userResponse.getData().getUserList());
                            hasMore = userResponse.getData().isHasMore();
                            progressBarVisibility.set(View.GONE);
                            noInternetVisibility.set(View.GONE);
                            recyclerViewVisibility.set(View.VISIBLE);
                            if (hasMore)
                                offset += 10;
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //here we will get the exception
                            progressBarVisibility.set(View.GONE);
                            noInternetVisibility.set(View.VISIBLE);
                            if (userList == null || userList.size() == 0)
                                recyclerViewVisibility.set(View.GONE);
                        }
                    });

            compositeDisposable.add(disposable);
        } else {
            progressBarVisibility.set(View.GONE);
            recyclerViewVisibility.set(View.GONE);
            if (userList == null || userList.size() == 0)
                noInternetVisibility.set(View.VISIBLE);
        }
    }

    public void changeUserDataSet(List<User> peoples) {
        userList.addAll(peoples);
        setChanged();
        notifyObservers();
    }

    public List<User> getUserList() {
        return userList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        // Remove Network Listener
        networkChangeReceiver.removeListener(this);
        context.unregisterReceiver(networkChangeReceiver);
        context = null;
    }

    private boolean isNetworkAvailable() {
        boolean connected=false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }

    @Override
    public void networkAvailable() {
        if (userList == null || userList.size() == 0)
            getUserListFromAPI();
    }

    @Override
    public void networkUnavailable() {
        progressBarVisibility.set(View.GONE);
        if (userList == null || userList.size() == 0) {
            recyclerViewVisibility.set(View.GONE);
            noInternetVisibility.set(View.VISIBLE);
        }
    }
}
