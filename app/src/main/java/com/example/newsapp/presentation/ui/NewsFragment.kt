package com.example.newsapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.presentation.screens.NewsScreen
import com.example.newsapp.presentation.vm.newsFeed.NewsViewModel



class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = ComposeView(requireContext())
        val viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        view.apply {
            setContent {
                NewsScreen(viewModel = viewModel)
            }
        }

        return view
    }


}