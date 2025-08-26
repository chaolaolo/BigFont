package com.example.bigfont;

import android.app.Application;

import com.example.bigfont.data.database.AppDatabase;

public class BigFontApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.getDatabase(this);
        AppDatabase.initializeData();
    }
}
