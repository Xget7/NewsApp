package com.example.newsapp.presentation.vm.favFeed

import androidx.compose.foundation.lazy.LazyListState
import com.example.newsapp.data.models.Article

data class FavoriteUiState (
    val isSuccessFromDb : Boolean = false,
    val isLoading: Boolean = false,
    val lazyState : LazyListState = LazyListState(0,0),
    val newsFavoriteList : MutableList<Article> = mutableListOf(),
    var isError : String? = null
)