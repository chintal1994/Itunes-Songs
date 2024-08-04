package com.encora.practical.api

import com.encora.practical.data.Feed
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("RSS/topsongs/limit=25/xml")
    fun getTopSongs(): Call<Feed>
}
