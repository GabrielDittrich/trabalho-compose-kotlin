package com.example.trabalhokotlincompose

import FilmeDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabalhokotlincompose.screen.FilmeListScreen
import com.example.trabalhokotlincompose.screen.LoginScreen
import com.example.trabalhokotlincompose.window.TelaDetalhadas
import com.example.trabalhokotlincompose.window.TelaMenu

@Composable
fun FilmeNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)  // Tela de login
        }
        composable("menu") {
            TelaMenu(navController)  // Tela menu de filmes
        }
        composable("filmeDetail/{filmeId}") { backStackEntry ->
            // Aqui você obtém o ID do filme a partir do back stack
            val filmeId = backStackEntry.arguments?.getString("filmeId")
            FilmeDetailScreen(navController = navController, filmeId = filmeId)
        }
        composable("taskList") {
            FilmeListScreen(navController)  // Tela de lista de filmes
        }
    }
}
