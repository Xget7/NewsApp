package com.example.newsapp.presentation.vm.newsFeed

import org.junit.Assert.*
import MainDispatcherRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.example.newsapp.presentation.vm.newsFeed.NewsUiState
import com.example.newsapp.presentation.vm.newsFeed.NewsViewModel
import com.example.newsapp.presentation.vm.searchNews.SearchNewsViewModel
import com.example.newsapp.repository.NewsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {


    @RelaxedMockK
    private lateinit var newsRepo : NewsRepository

    private lateinit var newsViewModel : SearchNewsViewModel




    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore(){
        val savedStateHandle = SavedStateHandle()
        MockKAnnotations.init(this)
        newsViewModel = SearchNewsViewModel(newsRepo,savedStateHandle)
        Dispatchers.setMain(mainDispatcherRule.dispatcher)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }


    @Test
    fun `when ViewModel Request Api Ui State Changes To Successful News Bool `() = runTest {
        //Given
        //When
        launch { newsViewModel.searchNewsByTopic() }


        //Then

        println(newsViewModel._uiState.value.newsList)
    }
}