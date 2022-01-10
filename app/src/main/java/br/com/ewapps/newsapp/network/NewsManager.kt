package br.com.ewapps.newsapp.network

import br.com.ewapps.newsapp.model.TopNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager (private val service: NewsService){

    suspend fun getArticles(country: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(country = country, "publishedAt")
    }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticleByCategory(category, "br", "publishedAt")

    }

    suspend fun getArticlesBySource(source: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesBySource(source, "pt", "publishedAt")
    }

    suspend fun getSearchedArticles(query: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticles(query, "pt", "publishedAt")
    }
}