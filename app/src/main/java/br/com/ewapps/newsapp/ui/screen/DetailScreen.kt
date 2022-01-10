package br.com.ewapps.newsapp.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.ewapps.newsapp.MainActivity
import br.com.ewapps.newsapp.R
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.model.MockData
import br.com.ewapps.newsapp.model.MockData.getTimeAgo
import br.com.ewapps.newsapp.model.Url
import com.google.gson.Gson
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(article: Article, scrollState: ScrollState, navController: NavController) {

    Scaffold(topBar = { DetailTopAppBar(onBackPressed = { navController.popBackStack() }) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CoilImage(
                article.urlToImage, contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author ?: "Não disponível")
                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(publishedAt = article.publishedAt!!).getTimeAgo()
                )
            }
            Text(text = article.title ?: "Não disponível", fontWeight = FontWeight.Bold)
            Text(
                text = article.description ?: "Não disponível",
                modifier = Modifier.padding(top = 16.dp)
            )
            Button(onClick = {
                val teste = Url(article.url!!)
                val json = Uri.encode(Gson().toJson(teste))

                navController.navigate("WebScreen/${json}")
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Leia a notícia completa!")

            }
        }
    }
}


@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = { Text(text = "Detalhes da Notícia", fontWeight = FontWeight.SemiBold) },

        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon, contentDescription = "Author", modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}


/*@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        /*Article(
            author = "Raja Razek, CNN",
            title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
            description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
            publishedAt = "2021-11-04T05:35:21Z"
        ),
        rememberScrollState(),
        rememberNavController()
    )*/
}*/