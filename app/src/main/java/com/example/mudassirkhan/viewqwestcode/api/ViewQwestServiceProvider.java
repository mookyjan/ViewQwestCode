package com.example.mudassirkhan.viewqwestcode.api;

import com.example.mudassirkhan.viewqwestcode.api.userList.UserListService;

public class ViewQwestServiceProvider implements IViewQwestProvider {

    private static volatile ViewQwestServiceProvider retrofitService;

    public static ViewQwestServiceProvider get() {
        if (retrofitService == null) {
            synchronized (ViewQwestServiceProvider.class) {
                if (retrofitService == null) {
                    retrofitService = new ViewQwestServiceProvider();
                }
            }
        }
        return retrofitService;
    }

    private ViewQwestServiceProvider() {
    }
    @Override
    public UserListService getUserList() {
        return RetrofitClient.getClient().create(UserListService.class);
    }
}
