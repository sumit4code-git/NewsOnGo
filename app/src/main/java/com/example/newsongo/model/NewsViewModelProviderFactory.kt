package com.example.newsongo.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsongo.repo.NewsRepository

class NewsViewModelProviderFactory (
    val app:Application,
    val newsRepository: NewsRepository
        ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository,app) as T
    }
}
