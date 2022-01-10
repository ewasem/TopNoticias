package br.com.ewapps.newsapp.ui.screen

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.ewapps.newsapp.R
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.model.MockData
import br.com.ewapps.newsapp.model.MockData.getTimeAgo
import br.com.ewapps.newsapp.model.getAllArticleCategory
import br.com.ewapps.newsapp.network.NewsManager
import br.com.ewapps.newsapp.ui.MainViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.coil.CoilImage

//Cria a tabela para selecionar as categorias de notícias que estão disponíveis.
@Composable
fun Categories(
    onFetchCategory: (String) -> Unit = {},
    viewModel: MainViewModel,
    navController: NavController
) {
    val tabsItems = getAllArticleCategory()
    Column {
        LazyRow {
            items(tabsItems.size) {
                val category = tabsItems[it]
                CategoryTab(
                    category = category.categoryName, categoryTranslated = category.categoryTranslated , onFetchCategory = onFetchCategory,
                    isSelected = viewModel.selectedCategory.collectAsState().value == category
                )
            }
        }
        ArticleContent(articles = viewModel.getArticleByCategory.collectAsState().value.articles ?: listOf(), navController = navController)
    }
}


@Composable
fun CategoryTab(category: String, categoryTranslated: String, isSelected: Boolean = false, onFetchCategory: (String) -> Unit) {
    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = categoryTranslated,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

//Cria o padrão que cada notícia recebida irá aparecer na lazyColumn
@Composable
fun ArticleContent(navController: NavController, articles: List<Article>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(articles){ article ->

            Card(
                modifier
                    .padding(8.dp)
                    .clickable {

                        val json = Uri.encode(Gson().toJson(article))

                        navController.navigate("DetailScreen/${json}")
                    },
                border = BorderStroke(1.dp, color = colorResource(id = R.color.purple_500)),
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.breaking_news),
                        error = painterResource(id = R.drawable.breaking_news)
                    )

                    Column(modifier.padding(8.dp)) {
                        Text(
                            text = article.title ?: "",
                            fontWeight = FontWeight.Bold,
                            maxLines = 3, overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = article.author ?: "", modifier = Modifier.fillMaxWidth(.5f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)

                            val time = article.publishedAt?.let { MockData.stringToDate(it).getTimeAgo() }

                            if (time == null) {
                                Text(text = "") } else {

                                Text(
                                    text = time
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/*@Preview
@Composable
fun ArticleContentPreview() {
    ArticleContent(
        articles = listOf(
            Article(
                author = "Namita Singh",
                title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
                description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
                publishedAt = "2021-12-06T04:42:40Z"
            )
        )
    )
}*/