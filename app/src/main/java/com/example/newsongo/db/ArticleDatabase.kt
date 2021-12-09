package com.example.newsongo.db

import android.content.Context
import androidx.room.*
import com.example.newsongo.Response.Article
import com.example.newsongo.dao.ArticleDao

@Database(entities = [Article::class], version = 2)
@TypeConverters(converter::class)
abstract class ArticleDatabase:RoomDatabase() {
    abstract fun getArticle(): ArticleDao
    companion object{
        @Volatile
        private var instance:ArticleDatabase?=null
        private var Lock=Any()

        operator fun invoke(context:Context)= instance?:synchronized(com.example.newsongo.db.ArticleDatabase.Companion.Lock){
            instance ?: createDatabase(context).also{ instance=it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db_db"
            ).build()
    }
}