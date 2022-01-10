package br.com.ewapps.newsapp.model

import br.com.ewapps.newsapp.model.ArticleCategory.*
enum class ArticleCategory(
    val categoryName: String,
    val categoryTranslated: String
) {
    BUSINESS("business", "negócios"),
    ENTERTAINMENT("entertainment", "entretenimento"),
    HEALTH("health", "saúde"),
    SCIENCE("science", "ciência"),
    SPORTS("sports", "esportes"),
    TECHNOLOGY("technology", "tecnologia")
}

fun getAllArticleCategory(): List<ArticleCategory> {
    return listOf(
        ArticleCategory.BUSINESS, ArticleCategory.ENTERTAINMENT, ArticleCategory.HEALTH, ArticleCategory.SCIENCE,
        ArticleCategory.SPORTS, ArticleCategory.TECHNOLOGY
    )
}

fun getArticleCategory(category: String) : ArticleCategory?{
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}