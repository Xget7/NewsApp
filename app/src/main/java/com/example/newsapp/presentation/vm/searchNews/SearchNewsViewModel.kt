package com.example.newsapp.presentation.vm.searchNews

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



    init {
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<String>(STATE_KEY_FILTER)?.let { q ->
            setQuery(q)
        }

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


    fun searchNewsByTopic(){
        viewModelScope.launch {
            repo.getArticlesFromTopic(searchQuery.value,selectedOption.value )
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