package com.example.newsapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.Source
import com.example.newsapp.presentation.ui.components.NewsFeed
import com.example.newsapp.presentation.vm.newsFeed.NewsUiState
import com.example.newsapp.presentation.vm.newsFeed.NewsViewModel

@Composable
fun NewsScreen(
    uiState: NewsUiState
) {
    if (uiState.isLoading) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.isSuccessNews) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFA76CD5))
        ) {
            Column(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Recent News From \uD83C\uDDFA\uD83C\uDDF8",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFFFFF)
                )
                NewsFeed(newsList = uiState.newsList, lazyListState = uiState.lazyState)
            }
        }
    } else if (uiState.isError != null) {
        Text("Error ${uiState.isError}", fontSize = 32.sp, color = Color.Red)
    }
}

@Preview
@Composable
fun NEwsScreenPrev() {
    val uiState = NewsUiState(true, false, LazyListState(0, 0), listOf( Article(
        "Jose ESpirito",
        "lorem ipsum asjkdjkalskd ",
        "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
        "10/2",
        Source("22132", "sajdks"),
        "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
        "http://192.168.0.1",
        "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
    ), Article(
        "Jose ESpirito",
        "lorem ipsum asjkdjkalskd ",
        "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
        "10/2",
        Source("22132", "sajdks"),
        "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
        "http://192.168.0.1",
        "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
    ), Article(
        "Jose ESpirito",
        "lorem ipsum asjkdjkalskd ",
        "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
        "10/2",
        Source("22132", "sajdks"),
        "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
        "http://192.168.0.1",
        "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
    )),null)
    NewsScreen(uiState = uiState)

}


@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    NewsScreen(uiState)
}

