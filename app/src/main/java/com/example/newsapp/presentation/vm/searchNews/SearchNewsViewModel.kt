package com.example.newsapp.presentation.vm.searchNews

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.presentation.vm.newsFeed.NewsUiState
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_QUERY = "news.state.query.key"
const val STATE_KEY_FILTER = "news.state.filter.key"



@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val repo: NewsRepository,
    private val savedStateHandle: SavedStateHandle,
    ) : ViewModel(){




    val _uiState = MutableStateFlow(SearchNewsUiState())
    val uiState: StateFlow<SearchNewsUiState> = _uiState.asStateFlow()


    val showDialog = mutableStateOf(false)
    val searchQuery = mutableStateOf("")
    val selectedOption = mutableStateOf("publishedAt")
    private val newsPage = mutableStateOf(1)



    init {

        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<String>(STATE_KEY_FILTER)?.let { q ->
            setQuery(q)
        }

        if (!repo.hasInternetConnection()){
            _uiState.update {
                it.copy(
                    isSuccessNews = false,
                    isLoading = false,
                    newsList = mutableListOf(),
                    isError = "No internet connection 💔"
                )
            }
        }

    }

    fun lazyStateScrolledToEnd(){
        newsPage.value += 1
        addNewsToList()
    }



    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onFilterChanged(filter: String) {
        setFilter(filter)
    }

    private fun setFilter(filter: String){
        this.selectedOption.value = filter
        savedStateHandle[STATE_KEY_FILTER] = filter
    }
    private fun setQuery(query: String){
        this.searchQuery.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }


    private fun addNewsToList(){
        val oldList =  _uiState.value.newsList.toMutableList()
        viewModelScope.launch {
            repo.getArticlesFromTopic(searchQuery.value,selectedOption.value , newsPage.value.toString() )
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
                    oldList.addAll(news)
                    _uiState.update {
                        it.copy(
                            isSuccessNews = true,
                            isLoading = false,
                            newsList = oldList,
                            isError = null
                        )
                    }
                }
        }
    }


    fun searchNewsByTopic(){
        Log.d("com.example.newsapp", "searchNewsByTocpic")
        viewModelScope.launch {
            repo.getArticlesFromTopic(searchQuery.value,selectedOption.value, "1")
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


}