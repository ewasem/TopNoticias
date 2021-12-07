package br.com.ewapps.newsapp.ui.screen

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.ewapps.newsapp.MainActivity
import br.com.ewapps.newsapp.R
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.model.MockData
import br.com.ewapps.newsapp.model.MockData.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(article: Article, scrollState: ScrollState, navController: NavController) {
    val context = LocalContext.current
    Scaffold(topBar = { DetailTopAppBar(onBackPressed = {navController.popBackStack()})}) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Detalhes da Notícia", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 16.dp))
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
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author?:"Não disponível")
                InfoWithIcon(icon = Icons.Default.DateRange, info = MockData.stringToDate(publishedAt = article.publishedAt!!).getTimeAgo())
            }
            Text(text = article.title?:"Não disponível", fontWeight = FontWeight.Bold)
            Text(text = article.description?:"Não disponível", modifier = Modifier.padding(top = 16.dp))
            Button(onClick = { val i = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(i)}, modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Leia a notícia completa!")
                
            }
        }
    }
}


@Composable
fun DetailTopAppBar(onBackPressed: ()-> Unit = {}){
    TopAppBar(title = { Text(text = "Tela de detalhe", fontWeight = FontWeight.SemiBold)},
    navigationIcon = {
        IconButton(onClick = {onBackPressed()}) {
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