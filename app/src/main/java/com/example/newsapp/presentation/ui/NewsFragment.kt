package com.example.newsapp.presentation.ui

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.presentation.screens.NewsScreen
import com.example.newsapp.presentation.vm.newsFeed.NewsViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgument
import com.example.newsapp.presentation.ui.components.WebViewPage
import com.example.newsapp.util.Routes


class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]

        // Inflate the layout for this fragment
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                NewsMainScreen(viewModel)
            }
        }
        return view
    }


}

@Composable
fun NewsMainScreen(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.NewsScreen.route) {
        composable(
            Routes.NewsScreen.route,
        ) {
            NewsScreen(viewModel = viewModel, navController)
        }
        composable(Routes.WebViewNews.route + "?url={url}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })
        ) {
            WebViewPage(
                url = it.arguments?.getString("url") ?: "google.com",
                navController
            )
        }
    }
}