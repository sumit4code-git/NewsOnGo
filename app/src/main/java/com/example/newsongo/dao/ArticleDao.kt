package com.example.newsongo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsongo.Response.Article
@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article : Article):Long
    @Query("SELECT * FROM articles")
    fun getAllarticle():LiveData<List<Article>>
    @Delete
    suspend fun delete(article: Article)
}