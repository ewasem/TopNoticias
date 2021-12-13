package br.com.ewapps.newsapp.network

import android.security.identity.AccessControlProfileId
import android.util.Log
import androidx.compose.runtime.*
import br.com.ewapps.newsapp.model.ArticleCategory
import br.com.ewapps.newsapp.model.TopNewsResponse
import br.com.ewapps.newsapp.model.getAllArticleCategory
import br.com.ewapps.newsapp.model.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsManager {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
    @Composable get() = remember {
        _newsResponse
    }

    val sourceName = mutableStateOf("techmundo.com.br")

    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val selectedCategory : MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getTopArticles("br")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _newsResponse.value = response.body()!!
                    Log.d("Notícias:", "${_newsResponse.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        }
        )

    }

    fun getArticlesByCategory(category: String) {
        val service = Api.retrofitService.getArticleByCategory(category,"br")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                    Log.d("Categoria:", "${_getArticleByCategory.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        }
        )

    }

    fun getArticlesBySource() {
        val service = Api.retrofitService.getArticlesBySource(sourceName.value, "pt")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _getArticleBySource.value = response.body()!!
                    Log.d("Categoria:", "${_getArticleBySource.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        }
        )

    }

    fun onSelectedCateforyChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}