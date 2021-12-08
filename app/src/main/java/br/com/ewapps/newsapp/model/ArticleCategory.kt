package br.com.ewapps.newsapp.model

import br.com.ewapps.newsapp.model.ArticleCategory.*
enum class ArticleCategory(
    val categoryName: String
) {
    BUSINESS("negócios"),
    ENTERTAINMENT("entretenimento"),
    GENERAL("geral"),
    HEALTH("saúde"),
    SCIENCE("ciência"),
    SPORTS("esportes"),
    TECHNOLOGY("tecnologia")
}

fun getAllArticleCategory(): List<ArticleCategory> {
    return listOf(
        ArticleCategory.BUSINESS, ArticleCategory.ENTERTAINMENT,
        ArticleCategory.GENERAL, ArticleCategory.HEALTH, ArticleCategory.SCIENCE,
        ArticleCategory.SPORTS, ArticleCategory.TECHNOLOGY
    )
}

fun getArticleCategory(category: String) : ArticleCategory?{
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}