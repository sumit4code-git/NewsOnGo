package com.example.newsongo.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.os.Build.VERSION_CODES.M
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsongo.MainActivity
import com.example.newsongo.NewsAplication
import com.example.newsongo.Response.Article
import com.example.newsongo.Response.NewsResponse
import com.example.newsongo.repo.NewsRepository
import com.example.newsongo.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(val repo:NewsRepository,app:Application): AndroidViewModel(app) {
val breakingNeews:MutableLiveData<Resource<NewsResponse>> =MutableLiveData()
    var breakingNewsPage=1
   var breakingNewsResponse:NewsResponse?=null
    val SearchNews:MutableLiveData<Resource<NewsResponse>> =MutableLiveData()
    var SearchNewsPage=1
    var searchNewsResponse:NewsResponse?=null
    init {
        getBreakingNews( "in")
    }
    fun getBreakingNews(CountryCode:String)=viewModelScope.launch {
        safeBreakingNewsCall(CountryCode)
    }
    fun searchNews(SearchQueery:String)=viewModelScope.launch {
        safeSearchNewsCall(SearchQueery)
    }
    private fun HandleNewsResponse(response:Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                breakingNewsPage++
                if(breakingNewsResponse==null){
                    breakingNewsResponse=resultResponse
                }
                else{
                    val oldArticle=breakingNewsResponse?.articles
                    val newArticle=resultResponse.articles

                    oldArticle?.addAll(newArticle)
                }
                return Resource.Sucess(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun HandleSearchNewsResponse(response:Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                SearchNewsPage++
                if(searchNewsResponse==null){
                    searchNewsResponse=resultResponse
                }
                else{
                    val oldArticle=searchNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Sucess(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    fun saveArticle(article:Article)=viewModelScope.launch {
      repo.upsert(article)
    }
    fun getSaveNews()=repo.getSavedNews()
    fun deleteArticle(article: Article)=viewModelScope.launch{
        repo.deleteArticle(article)
    }
    private suspend fun safeBreakingNewsCall(CountryCode:String){
        breakingNeews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()) {
                val response = repo.getBreakingNews(CountryCode, breakingNewsPage)
                breakingNeews.postValue(HandleNewsResponse(response))
            }
            else{
                breakingNeews.postValue(Resource.Error("NO Internet Connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException-> breakingNeews.postValue(Resource.Error("Network Failure"))
                    else -> breakingNeews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private suspend fun safeSearchNewsCall(SearchQuery:String){
        SearchNews.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()) {
                val response = repo.searchNews(SearchQuery, SearchNewsPage)
                SearchNews.postValue(HandleSearchNewsResponse(response))
            }
            else{
                SearchNews.postValue(Resource.Error("NO Internet Connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException-> SearchNews.postValue(Resource.Error("Network Failure"))
                else -> SearchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun hasInternetConnection():Boolean{
        val connectivityManager= getApplication<NewsAplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            val activenetwork=connectivityManager.activeNetwork?:return false
            val capabilities=connectivityManager.getNetworkCapabilities(activenetwork)?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI)-> true
                capabilities.hasTransport(TRANSPORT_CELLULAR)-> true
                capabilities.hasTransport(TRANSPORT_ETHERNET)-> true
                else ->false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI->true
                    TYPE_MOBILE ->true
                    TYPE_ETHERNET ->true
                    else ->false
                }
            }
        }
        return false;
    }
}
