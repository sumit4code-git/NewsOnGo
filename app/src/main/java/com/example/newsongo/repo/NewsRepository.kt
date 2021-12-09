package com.example.newsongo.repo

import com.example.newsongo.Response.Article
import com.example.newsongo.api.RetrofitInstance
import com.example.newsongo.db.ArticleDatabase

class NewsRepository(
    val db:ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingnews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.Searchnews(searchQuery,pageNumber)
    suspend fun upsert(article:Article)=db.getArticle().upsert(article)
  fun getSavedNews()=db.getArticle().getAllarticle()
    suspend fun deleteArticle(article:Article)=db.getArticle().delete(article)
}