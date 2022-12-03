package com.example.newsapp.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.presentation.ui.components.CustomDialog
import com.example.newsapp.presentation.ui.components.LoadingComponent
import com.example.newsapp.presentation.ui.components.NewsFeed
import com.example.newsapp.presentation.ui.components.SearchAppBar
import com.example.newsapp.presentation.ui.components.ShimmerNewsFeed
import com.example.newsapp.presentation.vm.searchNews.SearchNewsUiState
import com.example.newsapp.presentation.vm.searchNews.SearchNewsViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    uiState: SearchNewsUiState,
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    selectedFilter: String,
    onOptionSelected: (String) -> Unit,
    showDialog: MutableState<Boolean>,
    navHostController: NavHostController,
    scrolledToEnd: () -> Unit,
) {
    if (showDialog.value) {
        CustomDialog(
            setShowDialog = showDialog,
            onOptionSelected = onOptionSelected,
            selectedOption = selectedFilter
        )
    }
    if (uiState.isLoading) {
        Box(modifier = Modifier.background(Color(0xFF5c46bd))) {
            Scaffold(modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .background(Color(0xFF5c46bd)), topBar = {
                Row() {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = onQueryChanged,
                        onExecuteSearch = {
                            onExecuteSearch.invoke()
                        },
                        onOpenMenu = {
                            Log.d("openMenu , Value: ", showDialog.value.toString() )
                            showDialog.value = true
                        }
                    )

                }

            }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF5c46bd))
                ) {
                    Spacer(modifier = Modifier.height(74.dp))
                    ShimmerNewsFeed()
                }

            }
        }
    } else if (uiState.isSuccessNews) {
        Box(modifier = Modifier.background(Color(0xFF5c46bd))) {
            Scaffold(modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .background(Color(0xFF5c46bd)), topBar = {
                Row() {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = onQueryChanged,
                        onExecuteSearch = {
                            onExecuteSearch.invoke()
                        },
                        onOpenMenu = {
                            Log.d("openMenu , Value: ", showDialog.value.toString() )
                            showDialog.value = true
                        }
                    )

                }

            }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF5c46bd))
                ) {

                    Spacer(modifier = Modifier.height(74.dp))
                    NewsFeed(newsList = uiState.newsList, lazyListState = uiState.lazyState, { index , article -> }, navHostController )
                    if (uiState.lazyState.isScrolledToTheEnd()){
                        scrolledToEnd()
                    }
                }

            }
        }

    } else if (uiState.isError != null) {
        ErrorHandle(uiState.isError!!)
    } else {
        Box(modifier = Modifier.background(Color(0xFF5c46bd))) {
            Scaffold(modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
                {
                SearchAppBar(
                    query = query,
                    onQueryChanged = onQueryChanged,
                    onExecuteSearch = {
                    onExecuteSearch()
                    },
                    onOpenMenu = {
                        showDialog.value = true
                    }
                )
            }) {

                Box(Modifier.background(Color(0xFF5c46bd))) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Search For News",
                            fontSize = 38.sp,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }


            }
        }
    }
}



@Composable
fun SearchScreen(
    viewModel: SearchNewsViewModel = viewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val query = viewModel.searchQuery.value
    val selectedFilter = viewModel.selectedOption.value

    SearchScreen(
        uiState,
        query,
        viewModel::onQueryChanged,
        viewModel::searchNewsByTopic,
        selectedFilter,
        viewModel::onFilterChanged,
        viewModel.showDialog,
        navHostController
    ) { viewModel.lazyStateScrolledToEnd() }
}

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
