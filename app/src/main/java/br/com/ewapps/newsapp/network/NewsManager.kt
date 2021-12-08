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

    val selectedCategory : MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getTopArticles("br", Api.API_KEY)
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

    fun onSelectedCateforyChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}