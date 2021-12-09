package com.example.newsongo.db

import androidx.room.TypeConverter
import com.example.newsongo.Response.Source

class converter {
    @TypeConverter
    fun fromsource(source: Source): String {
        return source.name
    }
    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name,name)
    }
}