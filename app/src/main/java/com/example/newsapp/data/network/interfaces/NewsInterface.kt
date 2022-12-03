package com.example.newsapp.data.network.interfaces

import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.util.Const.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsInterface {

    @GET("top-headlines")
    suspend fun getArticlesFromCountry(
        @Query("country") countryId: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getArticlesFromTopic(
        @Query("q") topic: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("page") page: String = "1"
    ): Response<NewsResponse>

}