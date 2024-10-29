package com.example.trabalhokotlincompose.window

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TelaDetalhadas(navController: NavHostController, nomeLivro: String?) {
    Surface (modifier = Modifier.fillMaxSize()){ // Fundo da tela
        Column {
            Text("Livro: $nomeLivro ")

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                navController.popBackStack()
            }) {
                Text("Voltar")
            }
        }
    }
}