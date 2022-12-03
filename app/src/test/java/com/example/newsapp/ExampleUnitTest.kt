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
    fun findLongestSubsequence() {
        // Write your code here
        val arr = arrayOf(4,
            1,
            3,
            5,
            7)
        val sortedArr = arr.sorted().toMutableList()
        sortedArr.removeAt(0)
        val differencesList = mutableListOf<Int>()
        var difference = 0

        for (i in sortedArr.withIndex()) {
            if ((i.index + 1) < sortedArr.size) {
                difference = sortedArr[i.index + 1] - i.value
                differencesList.add(difference)
            }

        }
        val result = differencesList.sumOf { it }

        println(sortedArr)
        println(differencesList)


        println(result)
        assertEquals( 4 ,result)
    }

}