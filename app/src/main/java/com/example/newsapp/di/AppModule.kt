

package com.example.newsapp.di

import android.app.Application
import android.content.Context
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.ArticleDb
import com.example.newsapp.data.network.api.BASE_URL
import com.example.newsapp.data.network.interfaces.NewsInterface
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.NetworkConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule() {
    lateinit var database: ArticleDb


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ArticleDb {
        database = ArticleDb.invoke(context)
        return database
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: ArticleDb): ArticleDao{
        return db.getArticleDao()
    }
    @Singleton
    @Provides
    fun providesNetworkConnectivityHelper(@ApplicationContext context: Context): NetworkConnectivityHelper{
        return NetworkConnectivityHelper(context)
    }

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
    fun provideNewsRepo(api : NewsInterface , db: ArticleDb , networkConnectivityHelper: NetworkConnectivityHelper): NewsRepository{
        return NewsRepository(api, db, networkConnectivityHelper)
    }

}