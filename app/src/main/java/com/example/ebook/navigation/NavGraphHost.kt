package com.example.ebook.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ebook.Presentation.AddBookScreen
import com.example.ebook.Presentation.BooksByCategory
import com.example.ebook.Presentation.ShowPdfScreen
import com.example.ebook.Presentation.TabScreen


@Composable
fun NavGraphHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.HomeScreen) {
        composable<NavigationItem.HomeScreen> {
            TabScreen(navController)
        }

        composable<NavigationItem.BooksByCategory> {
            val category = it.toRoute<NavigationItem.BooksByCategory>()
            BooksByCategory(category = category.category, navController = navController)
        }

        composable<NavigationItem.ShowPdfScreen> {
            val res = it.toRoute<NavigationItem.ShowPdfScreen>()
            ShowPdfScreen(url = res.url, bookName = res.bookName, navController = navController)
        }

        composable<NavigationItem.AddBookScreen> {
            AddBookScreen(navController = navController)
        }
    }
}