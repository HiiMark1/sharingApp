package com.example.appproject

import android.app.Application
import com.example.appproject.di.AppComponent
import com.example.appproject.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}