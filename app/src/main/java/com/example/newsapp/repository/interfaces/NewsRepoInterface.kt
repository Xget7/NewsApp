package com.example.newsapp.repository.interfaces

import com.example.newsapp.data.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepoInterface {

    fun getArticlesFromCountry(countryId : String) : Flow<List<Article>>

     fun getArticlesFromTopic(topic : String , filter: String ) : Flow<List<Article>>

}