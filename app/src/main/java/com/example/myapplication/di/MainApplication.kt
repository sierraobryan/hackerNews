package com.example.myapplication.di

import android.app.Application

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