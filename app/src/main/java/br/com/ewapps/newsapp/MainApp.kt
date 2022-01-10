package br.com.ewapps.newsapp

import android.app.Application
import br.com.ewapps.newsapp.data.Repository
import br.com.ewapps.newsapp.network.Api
import br.com.ewapps.newsapp.network.NewsManager

class MainApp: Application() {
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager)
    }
}