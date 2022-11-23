package com.example.newsapp.presentation.vm.newsFeed

import androidx.compose.foundation.lazy.LazyListState
import com.example.newsapp.data.models.Article

data class NewsUiState(
    val isSuccessNews : Boolean = false,
    val isLoading: Boolean = false,
    val lazyState : LazyListState = LazyListState(0,0),
    val newsList : List<Article> = emptyList(),
    var isError : String? = null
)