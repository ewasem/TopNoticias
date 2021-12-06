package br.com.ewapps.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.ewapps.newsapp.model.MockData
import br.com.ewapps.newsapp.model.MockData.getTimeAgo
import br.com.ewapps.newsapp.model.NewsData

@Composable
fun TopNews(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Principais Notícias", fontWeight = FontWeight.SemiBold)
        LazyColumn {
            items(MockData.topNewsList){
                newsData ->
                TopNewsItem(newsData = newsData, onNewsClick = {
                    navController.navigate("DetailScreen/${newsData.id}")
                })
            }
        }

    }
}

@Composable
fun TopNewsItem(newsData: NewsData, onNewsClick: ()-> Unit = {}) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }) {
        Card(shape = RoundedCornerShape(10.dp)) {

            Image(
                painterResource(id = newsData.image), contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = MockData.stringToDate(publishedAt = newsData.publishedAt).getTimeAgo(), color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = newsData.title, color = Color.White, fontWeight = FontWeight.SemiBold)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(newsData = NewsData(
        1,
        author = "Raja Razek, CNN",
        title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
        description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
        publishedAt = "2021-11-04T05:35:21Z"
    ))
}