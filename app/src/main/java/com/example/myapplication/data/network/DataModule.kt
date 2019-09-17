package com.example.myapplication.data.network

import android.app.Application
import android.content.Context
import com.example.myapplication.di.Settings
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton

interface OkHttpSecurityModifier {
    fun apply(builder: OkHttpClient.Builder)
}

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache, securityModifier: OkHttpSecurityModifier): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.cache(cache)
        securityModifier.apply(builder)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(settings: Settings): String {
        return settings.baseUrl
    }

    @Singleton
    @Provides
    fun provideConverter(): Converter.Factory {
        val moshi = Moshi.Builder().build()
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGitHubApiService(retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGitHubService(
        context: Context,
        api: GitHubApiService): GitHubInteractor {
        return GitHubInteractor(context, api)
    }
}