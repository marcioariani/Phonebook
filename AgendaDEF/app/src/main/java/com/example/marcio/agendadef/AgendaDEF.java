package com.example.marcio.agendadef;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by marcio on 19/04/2016.
 */
public class AgendaDEF extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

    }
}
