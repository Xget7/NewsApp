package com.example.newsapp

import android.util.Log
import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.data.network.NewsApiClient
import com.example.newsapp.di.AppModule.provideNewsApi
import com.example.newsapp.repository.NewsRepository
import junit.framework.Assert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {



    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }



    @Test
    fun addition_isCorrecat() = runBlocking{
        val result = provideNewsApi().getArticlesFromTopic("bitcoin")
        assertEquals(result.isSuccessful, true)
    }

}