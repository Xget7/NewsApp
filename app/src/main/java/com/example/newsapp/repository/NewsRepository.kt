package com.example.newsapp.repository

import android.util.Log
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.data.network.NewsApiClient
import com.example.newsapp.data.network.interfaces.NewsInterface
import com.example.newsapp.repository.interfaces.NewsRepoInterface
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.security.auth.callback.Callback

class NewsRepository @Inject constructor(
    var apiClient: NewsInterface,
    private val refreshIntervalMs: Long = 30000
) : NewsRepoInterface {

    init {
        apiClient = NewsApiClient.newsApi
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

    override fun getArticlesFromTopic(topic: String, filter: String): Flow<List<Article>> = flow {
        val result = apiClient.getArticlesFromTopic(topic, filter)
        if (result.isSuccessful) {
            result.body()?.let { resul ->
                emit(resul.articles)
            }
        } else awaitCancellation()
    }


}