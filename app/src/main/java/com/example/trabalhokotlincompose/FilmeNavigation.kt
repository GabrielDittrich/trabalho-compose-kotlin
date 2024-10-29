package com.example.trabalhokotlincompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabalhokotlincompose.window.TelaDetalhadas
import com.example.trabalhokotlincompose.window.TelaMenu

@Composable
fun FilmeNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "menu" ){
        composable("menu") {
            // tela menu
            TelaMenu(navController)
        }
        composable ("detalhes/{nomeLivro}"){
            // detalhes livro
            val nomeLivro = it.arguments?.getString("nomeLivro")
            TelaDetalhadas(navController, nomeLivro)
        }
    }
}
