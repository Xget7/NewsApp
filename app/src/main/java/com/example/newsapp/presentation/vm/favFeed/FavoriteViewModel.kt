package com.example.newsapp.presentation.vm.favFeed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.data.models.Article
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


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repo: NewsRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(FavoriteUiState(isLoading = true))
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        getNewsFromDb()
    }


    private fun getNewsFromDb() {
        setLoading()
        viewModelScope.launch {
            repo.getAllFavArticles()
                .catch { msg ->
                    _uiState.update {
                        it.copy(
                            isSuccessFromDb = false,
                            isLoading = false,
                            newsFavoriteList = mutableListOf(),
                            isError = msg.localizedMessage
                        )
                    }
                }
                .collect { news ->
                    _uiState.update {
                        it.copy(
                            isSuccessFromDb = true,
                            isLoading = false,
                            newsFavoriteList = news.toMutableList(),
                            isError = null
                        )
                    }
                }
        }
    }


    fun saveArticle(article: Article) {
        viewModelScope.launch {
            repo.saveArticle(article)
            _uiState.value.newsFavoriteList.add(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repo.deleteArticle(article)
            getNewsFromDb()
        }
    }

    private fun setLoading() {
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }
    }

}