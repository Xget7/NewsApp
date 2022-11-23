package com.example.newsapp.presentation.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.Source


@Composable
fun NewsCard(
    article: Article,
    onClick : () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth().clickable {
        onClick()
    }, horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .width(320.dp)
                .padding(4.dp)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {

                SubcomposeAsyncImage(
                    model = article.urlToImage,
                    contentDescription = "desc"
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(240.dp)
                        .padding(start = 12.dp, end = 12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.description ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )

            }

        }
    }

}


@Composable
fun NewsFeed(
    newsList: List<Article>,
    lazyListState: LazyListState,

) {

    val context = LocalContext.current
    LazyColumn(
        state = lazyListState
    ) {

        items(newsList) {
            Spacer(modifier = Modifier.height(8.dp))
            NewsCard(article = it){
                context.startActivity(openWebNew(it.url))
            }
        }
    }

}

fun openWebNew(url : String) : Intent{
    val uris = Uri.parse(url)
    val intents = Intent(Intent.ACTION_VIEW, uris)
    val b = Bundle()
    b.putBoolean("new_window", true)
    return intents.putExtras(b)
}


@Preview
@Composable
fun PreviewNewsCard() {
    val fakeArt =
        Article(
            "Jose ESpirito",
            "lorem ipsum asjkdjkalskd ",
            "no se una descriptsion osea y para ser una descripcsion la idea es uq sea sau-per hamplica no cordino ni un dado ",
            "10/2",
            Source("22132", "sajdks"),
            "Una bazzoca cayo en iran hola 123 asjdksa sadjaksl sasd",
            "http://192.168.0.1",
            "https://www.pngpix.com/wp-content/uploads/2016/10/PNGPIX-COM-Android-PNG-Image.png"
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NewsCard(article = fakeArt){

            }

        }
    }

}