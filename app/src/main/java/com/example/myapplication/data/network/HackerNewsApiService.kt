package com.example.myapplication.data.network

import com.example.myapplication.data.model.Item
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApiService {

    @GET("item/{item-id}.json")
    fun getItem(@Path("item-id") itemId: Int): Single<Response<Item>>

    @GET("topstories.json")
    fun getTopStories(): Single<Response<List<Int>>>

    @GET("jobstories.json")
    fun getJobStories(): Single<Response<List<Int>>>

    @GET("askstories.json")
    fun getAskStories(): Single<Response<List<Int>>>

    @GET("showstories.json")
    fun getShowStories(): Single<Response<List<Int>>>

}