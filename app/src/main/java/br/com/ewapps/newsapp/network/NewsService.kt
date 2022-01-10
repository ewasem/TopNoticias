package br.com.ewapps.newsapp.network

import br.com.ewapps.newsapp.model.TopNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country") country: String,
        @Query("sortBy") sortBy: String
    ): TopNewsResponse

    @GET("top-headlines")
    suspend fun getArticleByCategory(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("sortBy") sortBy: String
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticlesBySource(
        @Query("domains") domains: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String
    ): TopNewsResponse

}