package com.example.testapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);
    }
}