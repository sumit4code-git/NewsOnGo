package com.example.newsongo.fragment

import android.os.Build
import android.os.Bundle
import android.text.AlteredCharSequence.make
import android.text.BoringLayout.make
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsongo.MainActivity
import com.example.newsongo.R
import com.example.newsongo.model.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment:Fragment(R.layout.fragment_article) {
    lateinit var viewModel:NewsViewModel
    val args:ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        val article =args.article
        webView.apply {
            webViewClient= WebViewClient()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                webViewClient.onPageStarted(webView,article.url,null).apply {
//                    make(view,"Deleted Successfully",Snackbar.LENGTH_LONG).show()
//                }
//                webViewClient.onPageFinished(webView,article.url).apply {
//                    if(article.author==null){
//                        fab.alpha= 0f
//                    }
//                }
//            }
            article.url?.let { loadUrl(it) }
        }
        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}

