package com.example.newsapp.util

sealed class Routes(val route: String) {
    object NewsScreen  : Routes("newsScreen")
    object WebViewNews  : Routes("webViewNews")

}