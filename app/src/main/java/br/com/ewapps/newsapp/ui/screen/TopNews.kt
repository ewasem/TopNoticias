package br.com.ewapps.newsapp.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.model.MockData
import br.com.ewapps.newsapp.model.MockData.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage
import br.com.ewapps.newsapp.R
import br.com.ewapps.newsapp.components.ErrorUI
import br.com.ewapps.newsapp.components.LoadingUI
import br.com.ewapps.newsapp.components.SearchBar
import br.com.ewapps.newsapp.ui.MainViewModel
import com.google.gson.Gson

@Composable
fun TopNews(
    navController: NavController,
    articles: List<Article>,
    query: MutableState<String>,
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Text(text = "Principais Notícias", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
        SearchBar(query = query, viewModel = viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<Article>()
        if (searchedText != "") {
            resultList.addAll(
                viewModel.getSearchedArticle.collectAsState().value.articles ?: articles
            )
        } else {
            resultList.addAll(articles)
        }
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                LazyColumn {
                    items(resultList.size) { index ->

                        TopNewsItem(article = resultList[index],
                            onNewsClick = {
                                val json = Uri.encode(Gson().toJson(resultList[index]))

                                println(json)
                                navController.navigate("DetailScreen/${json}")
                            })
                    }
                }
            }
        }


    }
}

@Composable
fun TopNewsItem(article: Article, onNewsClick: () -> Unit = {}) {
    Box(modifier = Modifier
        .height(330.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }) {
        Card(
            shape = RoundedCornerShape(10.dp), elevation = 5.dp, modifier = Modifier
                .height(200.dp)
        ) {
            CoilImage(
                article.urlToImage, contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 205.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val time = article.publishedAt?.let { MockData.stringToDate(it).getTimeAgo() }

            if (time == null) {
                Text(
                    text = "",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
            } else {

                Text(
                    text = time,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(text = article.title ?: "", color = Color.Gray, fontWeight = FontWeight.SemiBold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(
        Article(

            author = "Raja Razek, CNN",
            title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
            description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
            publishedAt = "2021-11-04T05:35:21Z"
        )
    )
}