package br.com.ewapps.newsapp.model


//Classe responsável por receber os dados do Retrofit e ser o modelo de dados do JSON
data class TopNewsResponse(
    val articles: List<Article>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)



