package com.example.newsapp.presentation.vm.newsFeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Article
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repo: NewsRepository
) : ViewModel() {

    private val recentNews: Flow<MutableList<Article>> = emptyFlow()

    val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()


    init {
        getNews()
    }

    private fun getNews() {
        setLoading()
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            repo.getArticlesFromCountry("us")
                .catch { msg ->
                    _uiState.update {
                        it.copy(
                            isSuccessNews = false,
                            isLoading = false,
                            newsList = emptyList(),
                            isError = msg.localizedMessage
                        )
                    }
                }
                .collect { news ->
                    _uiState.update {
                        it.copy(
                            isSuccessNews = true,
                            isLoading = false,
                            newsList = news,
                            isError = null
                        )
                    }
                }
        }
    }

    private fun setLoading(){
        _uiState.update {
            it.copy(
                isLoading =true,
            )
        }
    }



//    .catch {
//        it.localizedMessage?.let { it1 -> NewsUiState.Error(it1) }
//    }.collect{ news ->
//        recentNews.map {
//            it.addAll(news)
//        }
//    }
}