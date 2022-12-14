package com.example.newsapp.data.network.api

import com.example.newsapp.data.network.interfaces.NewsInterface
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/v2/"


object NewsApiClient {

   val newsApi: NewsInterface by lazy {
       Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(NewsInterface::class.java)
   }



}
