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


@ExperimentalAnimationApi
@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

        MainScreen(navController = navController, scrollState = scrollState, mainViewModel)

}

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {

    Scaffold(
        bottomBar = {

                BottomMenu(navController = navController)


    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {

    val articles = mutableListOf<Article>()
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
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
            val isLoading = mutableStateOf(loading)
            val isError = mutableStateOf(error)
            bottomNavigation(navController = navController, articles, viewModel, query = queryState, isError = isError, isLoading = isLoading)

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
                urlString?.let{
                    WebScreen(urlString)
                }
            }

        }
    }
}


fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    article: List<Article>,
    viewModel: MainViewModel,
    query: MutableState<String>,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    composable(BottomMenuScreen.TopNews.route) {

        TopNews(navController = navController, articles = article, query, viewModel, isLoading = isLoading, isError = isError)

    }
    composable(BottomMenuScreen.Categories.route) {


        Categories(viewModel = viewModel, onFetchCategory = {

            if (it == "") {
                viewModel.onSelectedCategoryChanged("business")
                viewModel.getArticlesByCategory("business")
            } else {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            }



        }, navController = navController, isLoading = isLoading, isError = isError)
    }
    composable(BottomMenuScreen.Sources.route) {


        Sources(viewModel, isError = isError, isLoading = isLoading)
    }

}