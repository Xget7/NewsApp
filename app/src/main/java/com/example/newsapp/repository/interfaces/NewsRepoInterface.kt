package com.example.newsapp.repository.interfaces

import com.example.newsapp.data.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepoInterface {

    fun hasInternetConnection(): Boolean

    fun getArticlesFromCountry(countryId : String) : Flow<List<Article>>

     fun getArticlesFromTopic(topic : String , filter: String, pageCount : String? ) : Flow<List<Article>>

     suspend fun saveArticle(article: Article)

    fun deleteArticle(article: Article)

    fun getAllFavArticles() : Flow<List<Article>>

}