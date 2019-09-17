package com.example.myapplication.di

import com.example.myapplication.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    AppModule::class])
interface ApplicationComponent {
    fun inject(application: MainApplication)

    fun inject(activity: MainActivity)
}