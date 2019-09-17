package com.example.myapplication.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AppModule(private val loadingDelayMs: Long = LOADING_DELAY_MS) {

    @Provides
    fun provideMainApplicationInitializer(application: Application): MainApplicationInitializer {
        return MainApplicationInitializer(application)
    }

    @Provides
    fun provideSettings(context: Context) = Settings(context)

    @Provides
    @Named("loading_delay_ms")
    fun loadingDelayMs() = loadingDelayMs

    companion object {
        private const val LOADING_DELAY_MS: Long = 500
    }
}
