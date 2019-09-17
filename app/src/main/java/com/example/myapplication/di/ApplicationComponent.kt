package com.example.myapplication.di

import com.example.myapplication.data.network.DataModule
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.main.ViewModelFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    AppModule::class,
    DataModule::class,
    ViewModelFactoryModule::class])
interface ApplicationComponent {
    fun inject(application: MainApplication)

    fun inject(activity: MainActivity)
}