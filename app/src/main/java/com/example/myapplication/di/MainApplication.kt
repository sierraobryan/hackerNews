package com.example.myapplication.di

import android.app.Application
import androidx.annotation.VisibleForTesting
import javax.inject.Inject

open class MainApplication : Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .androidModule(AndroidModule(this))
            .build()
        component.inject(this)
    }
}