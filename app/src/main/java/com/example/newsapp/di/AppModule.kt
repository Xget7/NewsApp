

package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.data.network.BASE_URL
import com.example.newsapp.data.network.NewsApiClient
import com.example.newsapp.data.network.interfaces.NewsInterface
import com.example.newsapp.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideNewsApi(): NewsInterface{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsInterface::class.java)
    }


    @Singleton
    @Provides
    fun provideNewsRepo(api : NewsInterface): NewsRepository{
        return NewsRepository(api)
    }

}