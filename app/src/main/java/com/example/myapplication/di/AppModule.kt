package com.example.myapplication.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AppModule {

    @Provides
    fun provideSettings(context: Context) = Settings(context)
}
