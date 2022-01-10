package br.com.ewapps.newsapp.data

import br.com.ewapps.newsapp.network.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("br")
    suspend fun getArticlesByCategory(category: String) =
        manager.getArticlesByCategory(category = category)
    suspend fun getArticlesBySource(source: String) = manager.getArticlesBySource(source)
    suspend fun getSearchedArticles(query: String) = manager.getSearchedArticles(query)
}