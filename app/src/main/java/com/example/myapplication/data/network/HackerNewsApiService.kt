package com.example.myapplication.data.network

import retrofit2.http.GET

interface HackerNewsApiService {

    @GET("item/{item-id}.json")
    get

}