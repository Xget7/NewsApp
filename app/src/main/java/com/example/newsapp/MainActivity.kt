package com.example.newsapp

import android.app.Activity
import android.app.AppComponentFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.presentation.ui.InfoFragment
import com.example.newsapp.presentation.ui.NewsFragment
import com.example.newsapp.presentation.ui.SearchFragment
import com.example.newsapp.presentation.ui.adapter.MainViewPagerAdapter
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.mainViewPager)
        tabLayout = findViewById(R.id.mainTabLayout)

        val fragmentsList = arrayListOf(
            NewsFragment(),
            SearchFragment(),
            InfoFragment()
        )
        val adapter = MainViewPagerAdapter(fragmentsList, this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "Home"}
                    1 -> { tab.text = "Search"}
                    2 -> { tab.text = "Info"}

                }
            }).attach()
    }



}



