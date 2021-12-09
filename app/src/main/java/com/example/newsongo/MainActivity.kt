package com.example.newsongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsongo.db.ArticleDatabase
import com.example.newsongo.model.NewsViewModel
import com.example.newsongo.model.NewsViewModelProviderFactory
import com.example.newsongo.repo.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel:NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository=NewsRepository(ArticleDatabase(this))
        val viewmodelProviderFactory=NewsViewModelProviderFactory(application,repository)
        viewModel=ViewModelProvider(this,viewmodelProviderFactory).get(NewsViewModel::class.java)
//        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController= navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}