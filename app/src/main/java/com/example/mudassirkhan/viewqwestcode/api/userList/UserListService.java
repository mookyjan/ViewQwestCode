package com.example.mudassirkhan.viewqwestcode.api.userList;

import com.example.mudassirkhan.viewqwestcode.model.UserListResponse;
import com.example.mudassirkhan.viewqwestcode.utils.AppConstant;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserListService {

    @GET(AppConstant.USER_LIST)
    Observable<UserListResponse> getUserList(@Query("offset") int offset, @Query("limit") int limit);
}
