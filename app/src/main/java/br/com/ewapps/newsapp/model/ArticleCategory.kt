package br.com.ewapps.newsapp.model

import br.com.ewapps.newsapp.model.ArticleCategory.*
enum class ArticleCategory(
    val categoryName: String
) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("sciense"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
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