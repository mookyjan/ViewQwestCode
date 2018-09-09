package com.example.mudassirkhan.viewqwestcode;

import android.app.Application;
import android.content.Context;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ViewQwestApplication extends Application {

  private Scheduler scheduler;

  private static ViewQwestApplication get(Context context) {
    return (ViewQwestApplication) context.getApplicationContext();
  }

  public static ViewQwestApplication create(Context context) {
    return ViewQwestApplication.get(context);
  }

  public Scheduler subscribeScheduler() {
    if (scheduler == null) {
      scheduler = Schedulers.io();
    }

    return scheduler;
  }

  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }
}
