package br.com.ewapps.newsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

// Clase responsável por criar o Bottom Menu
sealed class BottomMenuScreen(val route: String,
val icon: ImageVector, val title: String){
    object TopNews: BottomMenuScreen("top news", icon = Icons.Outlined.Home, "Notícias")
    object Categories: BottomMenuScreen("categories", icon = Icons.Outlined.Category, "Categorias")
    object Sources: BottomMenuScreen("sources", icon = Icons.Outlined.Edit, "Fontes")
}
