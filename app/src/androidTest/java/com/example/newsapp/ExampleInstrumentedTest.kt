package com.example.newsapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsapp.data.network.api.NewsApiClient.newsApi
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun apiTest() = runBlocking{
        // Context of the app under test.
        val response = newsApi.getArticlesFromTopic("BITCOIN").body()
        assertEquals("ok", response?.status)
    }
}