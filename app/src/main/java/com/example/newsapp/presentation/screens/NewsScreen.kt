package com.example.newsapp.presentation.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.Source
import com.example.newsapp.presentation.ui.components.NewsFeed
import com.example.newsapp.presentation.ui.components.ShimmerNewsFeed
import com.example.newsapp.presentation.vm.newsFeed.NewsUiState
import com.example.newsapp.presentation.vm.newsFeed.NewsViewModel

@Composable
fun NewsScreen(
    uiState: NewsUiState,
    onFav: (Int?, Article?) -> Unit,
    navController: NavHostController
) {
    if (uiState.isLoading) {
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
                ShimmerNewsFeed()
            }
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
                NewsFeed(
                    newsList = uiState.newsList,
                    lazyListState = uiState.lazyState,
                    onFav = onFav,
                    navController
                )
            }
        }
    } else if (uiState.isError != null) {
        ErrorHandle(uiState.isError!!)

    }
}


@Preview
@Composable
fun NEwsScreenPrev() {
    val uiState = NewsUiState(
        true, false, LazyListState(0, 0), listOf(
            Article(
                id = 2,
                false,
                "Jose ESpirito",
                "lorem ipsum asjkdjkalskd ",
                "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
                "10/2",
                Source("22132", "sajdks"),
                "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
                "http://192.168.0.1",
                "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
            ), Article(
                id = 3,
                false,
                "Jose ESpirito",
                "lorem ipsum asjkdjkalskd ",
                "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
                "10/2",
                Source("22132", "sajdks"),
                "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
                "http://192.168.0.1",
                "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
            ), Article(
                id = 4,
                false,
                "Jose ESpirito",
                "lorem ipsum asjkdjkalskd ",
                "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
                "10/2",
                Source("22132", "sajdks"),
                "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
                "http://192.168.0.1",
                "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
            )
        ) as MutableList<Article>, null
    )
//    NewsScreen(uiState = uiState, {}, NavController(LocalContext.current))

}

@Composable
fun ErrorHandle(error: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(error, fontSize = 32.sp, color = Color.Red , modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
    }
}


@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val mContext = LocalContext.current

    NewsScreen(uiState = uiState, onFav = { index, article ->
        if (article != null) {
            if (index != null) {
                viewModel.saveArticle(index, article = article)
            }
        }
        Toast.makeText(mContext, "Article saved into favorites", Toast.LENGTH_LONG).show()
    }, navController)


}

