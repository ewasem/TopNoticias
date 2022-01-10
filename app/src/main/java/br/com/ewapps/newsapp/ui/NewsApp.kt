package br.com.ewapps.newsapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.ewapps.newsapp.BottomMenuScreen
import br.com.ewapps.newsapp.components.BottomMenu
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.model.AssetParamType
import br.com.ewapps.newsapp.model.AssetParamTypeUrl
import br.com.ewapps.newsapp.model.Url
import br.com.ewapps.newsapp.ui.screen.*

@SuppressLint("UnrememberedMutableState")
@ExperimentalAnimationApi
@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    val visible: MutableState<Boolean> = mutableStateOf(true)
        MainScreen(navController = navController, scrollState = scrollState, mainViewModel, visible)

}

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel,
    visible: MutableState<Boolean>
) {

    Scaffold(
        bottomBar = {

            if (visible.value) {
                BottomMenu(navController = navController)
            }

    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel,
            visible = visible
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    visible: MutableState<Boolean>
) {

    val articles = mutableListOf<Article>()
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())
    Log.d("Notícias", "$articles")
    articles?.let {

        NavHost(
            navController = navController,
            startDestination =
            BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            val queryState = mutableStateOf(viewModel.query.value)
            bottomNavigation(navController = navController, articles, viewModel, query = queryState, visible = visible)

            composable(
                "DetailScreen/{article}",
                arguments = listOf(
                    navArgument("article") {
                        type = AssetParamType()
                    }
                )
            ) {
                val article = it.arguments?.getParcelable<Article>("article")
                article?.let {
                    if (queryState.value != "") {
                        articles.clear()
                        articles.addAll(
                            viewModel.getSearchedArticle.collectAsState().value.articles ?: listOf()
                        )
                    } else {
                        articles.clear()
                        articles.addAll(viewModel.newsResponse.collectAsState().value.articles ?: listOf())
                    }

                    visible.value = false
                    DetailScreen(article, scrollState, navController)
                }

            }
            composable(
                "WebScreen/{url}",
                arguments = listOf(navArgument("url") {
                    type = AssetParamTypeUrl()
                })


            ) {
                val urlString = it.arguments?.getParcelable<Url>("url")

                visible.value
                WebScreen(urlString!!)
            }

        }
    }
}


fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    article: List<Article>,
    viewModel: MainViewModel,
    query: MutableState<String>,
    visible: MutableState<Boolean>
) {
    composable(BottomMenuScreen.TopNews.route) {
        visible.value = true
        TopNews(navController = navController, articles = article, query, viewModel)

    }
    composable(BottomMenuScreen.Categories.route) {

        visible.value = true
        Categories(viewModel = viewModel, onFetchCategory = {

            if (it == "") {
                viewModel.onSelectedCategoryChanged("business")
                viewModel.getArticlesByCategory("business")
            } else {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            }



        }, navController = navController)
    }
    composable(BottomMenuScreen.Sources.route) {

        visible.value = true
        Sources(viewModel)
    }

}