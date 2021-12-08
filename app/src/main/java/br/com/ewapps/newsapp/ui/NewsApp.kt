package br.com.ewapps.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.ewapps.newsapp.BottomMenuScreen
import br.com.ewapps.newsapp.MainActivity
import br.com.ewapps.newsapp.components.BottomMenu
import br.com.ewapps.newsapp.model.Article
import br.com.ewapps.newsapp.network.NewsManager
import br.com.ewapps.newsapp.ui.screen.Categories
import br.com.ewapps.newsapp.ui.screen.DetailScreen
import br.com.ewapps.newsapp.ui.screen.Sources
import br.com.ewapps.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState = scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController = navController, scrollState = scrollState, paddingValues = it)

    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {

    val articles = newsManager.newsResponse.value.articles
    Log.d("Notícias", "$articles")
    articles?.let {
        NavHost(navController = navController, startDestination =
        BottomMenuScreen.TopNews.route, modifier = Modifier.padding(paddingValues = paddingValues)) {
            bottomNavigation(navController = navController, articles, newsManager)

            composable("DetailScreen/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let{
                    val article = articles[index]
                    DetailScreen(
                        article = article,
                        scrollState = scrollState,
                        navController = navController
                    )
                }

            }
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavController, article: List<Article>, newsManager: NewsManager) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles = article)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories(newsManager = newsManager, onFetchCategory = {
            newsManager.onSelectedCateforyChanged(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }

}