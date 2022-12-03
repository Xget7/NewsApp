package com.example.newsapp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.app.Application
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import android.util.Log
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.local.ArticleDb
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.network.api.NewsApiClient
import com.example.newsapp.data.network.interfaces.NewsInterface
import com.example.newsapp.repository.interfaces.NewsRepoInterface
import com.example.newsapp.util.NetworkConnectivityHelper
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private var apiClient: NewsInterface,
    private val db: ArticleDb,
    val networkConnectivityHelper: NetworkConnectivityHelper
) : NewsRepoInterface {

    private val dao = db.getArticleDao()


    init {
        apiClient = NewsApiClient.newsApi
    }

    override fun hasInternetConnection() : Boolean {
        return networkConnectivityHelper.isNetworkAvailable()
    }


    override fun getArticlesFromCountry(countryId: String): Flow<List<Article>> = flow {
        val result = apiClient.getArticlesFromCountry(countryId)
        Log.d("ResultFromApi", result.body().toString())
        if (result.isSuccessful) {
            result.body()?.let { result ->
                emit(result.articles)
            }
        } else awaitCancellation()
    }

    override fun getArticlesFromTopic(
        topic: String,
        filter: String,
        pageCount: String?
    ): Flow<List<Article>> = flow {
        val result =
            apiClient.getArticlesFromTopic(
                topic = topic,
                sortBy = filter,
                page = pageCount ?: "1")
        if (result.isSuccessful) {
            result.body()?.let { resul ->
                emit(resul.articles)
            }
        } else awaitCancellation()
    }

    override suspend fun saveArticle(article: Article) {
        dao.insert(article)
    }

    override fun deleteArticle(article: Article) {
        dao.deleteArticle(article)
    }

    override fun getAllFavArticles(): Flow<List<Article>> = dao.getAllFavNews()


}