package com.example.newsapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.newsapp.R
import com.example.newsapp.presentation.screens.SearchScreen
import com.example.newsapp.presentation.vm.newsFeed.NewsViewModel
import com.example.newsapp.presentation.vm.searchNews.SearchNewsViewModel


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        val viewModel = ViewModelProvider(requireActivity())[SearchNewsViewModel::class.java]
        view.apply {
            setContent {
                SearchScreen(viewModel)
            }
        }

        return view
    }


}