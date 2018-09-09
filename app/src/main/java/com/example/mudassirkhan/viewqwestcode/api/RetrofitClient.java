package com.example.mudassirkhan.viewqwestcode.api;

import com.example.mudassirkhan.viewqwestcode.utils.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit retrofit;

    private RetrofitClient() {
    }

    public static Retrofit getClient() {
        Retrofit instance = retrofit;
        if (instance == null) {
            synchronized (RetrofitClient.class) {

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        return chain.proceed(originalRequest);
                    }
                }).build();
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create();
                instance = retrofit;
                if (instance == null) {
                    instance = retrofit = new Retrofit.Builder()
                            .baseUrl(AppConstant.BASE_URL)
                            .client(okHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                            .addCallAdapterFactory(new ErrorHandlingAdapter.ErrorHandlingCallAdapterFactory())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }

        return instance;
    }
}
