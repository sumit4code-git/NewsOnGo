package com.example.newsongo.api
import com.androiddevs.mvvmnewsapp.ui.utils.Constants.Companion.APIKEY
import com.example.newsongo.Response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingnews(
        @Query("country")
        countryCode:String="id",
        @Query("page")
        pagesize:Int=1,
        @Query("apikey")
        apikey: String =APIKEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun Searchnews(
        @Query("q")
        SearchQuery:String="id",
        @Query("page")
        pagesize:Int=1,
        @Query("apikey")
        apikey: String =APIKEY
    ):Response<NewsResponse>
}