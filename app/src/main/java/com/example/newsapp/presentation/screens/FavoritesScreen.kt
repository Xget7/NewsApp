package com.example.newsapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.models.Article
import com.example.newsapp.presentation.ui.components.FavNewsFeed
import com.example.newsapp.presentation.ui.components.LoadingComponent
import com.example.newsapp.presentation.ui.components.NewsFeed
import com.example.newsapp.presentation.vm.favFeed.FavoriteUiState
import com.example.newsapp.presentation.vm.favFeed.FavoriteViewModel
import com.example.newsapp.presentation.vm.searchNews.SearchNewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import me.saket.swipe.SwipeAction


@Composable
fun FavoritesScreen(
    uiState: FavoriteUiState,
    onFav: (Article) -> Unit,
    onUnFav: (Article) -> Unit,
    navController: NavHostController
) {


    if (uiState.isLoading) {
        LoadingComponent()
    } else if (uiState.isSuccessFromDb) {
        FavoriteNewsScreen(uiState.newsFavoriteList, uiState.lazyState, {
            onFav(it)
        },{
          onUnFav(it)
        }, navController)
    } else if (uiState.isError != null) {
        ErrorHandle(uiState.isError!!)
    }
}


@Composable
fun FavoriteNewsScreen(
    favoriteNews: List<Article>,
    lazyState: LazyListState,
    onFav: (Article) -> Unit,
    onUnFav: (Article) -> Unit,
    navController: NavHostController
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(Modifier.padding(top = 20.dp)) {
            Text(
                text = "Favorite News ❤ ",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF000000)
            )
            FavNewsFeed(newsList = favoriteNews, lazyListState = lazyState, {
                onFav(it)
            },  navController = navController, onUnFav = {
                onUnFav(it)
            })
        }
    }
}

@Composable
fun FavoritesScreen(
    viewModel: FavoriteViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    FavoritesScreen(uiState, {
        viewModel.saveArticle(it)
    }, {
       viewModel.deleteArticle(it)
        Toast.makeText(context, "Removed.", Toast.LENGTH_LONG).show()
    },navController)

}



