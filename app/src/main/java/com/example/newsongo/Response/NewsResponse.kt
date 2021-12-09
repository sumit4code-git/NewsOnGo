package com.example.newsongo.Response

import com.example.newsongo.Response.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)