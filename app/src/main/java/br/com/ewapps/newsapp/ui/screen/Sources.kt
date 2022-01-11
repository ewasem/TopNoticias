package br.com.ewapps.newsapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.ewapps.newsapp.R
import br.com.ewapps.newsapp.components.ErrorUI
import br.com.ewapps.newsapp.components.LoadingUI
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.ui.MainViewModel

@Composable
fun Sources(
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    val items = listOf(
        "R7" to "r7.com",
        "UOL" to "uol.com.br",
        "G1" to "globo.com",
        "CNN Brasil" to "cnnbrasil.com.br",
        "Tecmundo" to "tecmundo.com.br",
        "Canaltech" to "canaltech.com.br"
    )
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Fonte: ${viewModel.sourceName.collectAsState().value}") },
            actions = {
                var menuExpanded by remember { mutableStateOf(false) }
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = {
                            menuExpanded = false
                        },
                    ) {
                        items.forEach {
                            DropdownMenuItem(onClick = {
                                viewModel.sourceName.value = it.second
                                viewModel.getArticlesBySource()
                                menuExpanded = false
                            }) {
                                Text(text = it.first)
                            }
                        }
                    }
                }
            })
    }) {
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                viewModel.getArticlesBySource()
                val articles = viewModel.getArticleBySource.collectAsState().value
                SourceContent(articles = articles.articles ?: listOf())
            }
        }
    }
}


@Composable
fun SourceContent(articles: List<Article>) {
    val uriHandler = LocalUriHandler.current
    LazyColumn {
        items(articles) { article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(id = R.color.purple_500),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Leia a reportagem completa!")
                }
            }

            Card(
                backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp, modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp, start = 8.dp)
                        .height(200.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = article.title ?: "",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = article.description ?: "",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Card(backgroundColor = Color.White, elevation = 6.dp) {
                        ClickableText(text = annotatedString, Modifier.padding(8.dp), onClick = {
                            annotatedString.getStringAnnotations(it, it)
                                .firstOrNull()
                                ?.let { result ->
                                    if (result.tag == "URL") {
                                        uriHandler.openUri(result.item)
                                    }
                                }
                        })
                    }
                }
            }
        }
    }
}