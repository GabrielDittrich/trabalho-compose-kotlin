package com.example.trabalhokotlincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trabalhokotlincompose.data.Filme
import com.example.trabalhokotlincompose.ui.theme.TrabalhoKotlinComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingPreview()

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrabalhoKotlinComposeTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 10.dp,
            border = BorderStroke(0.1.dp, Color.LightGray)
        ) {

            var filmes = listOf(
                Filme(0, "O Senhor dos Aneis", "1954"),
                Filme(1, "1984", "1949"),
                Filme(2, "Dom Quixote", "1605")
            )

            ListaDeFilmes(filmes)

        }

    }
}

@Composable
fun ListaDeFilmes(filmes: List<Filme>){
    LazyColumn {
        items(filmes){ filme ->
            CardLivro(filme)
        }
    }
}

@Composable
fun CardLivro(filme: Filme){
    Card (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ){

        Column (
            modifier = Modifier.padding(6.dp)
        ){
            Text("ID: ${filme.id}", style = MaterialTheme.typography.bodySmall)
            Text("Nome: ${filme.nome}", style = MaterialTheme.typography.headlineLarge)
            Text("Ano: ${filme.ano}", style = MaterialTheme.typography.bodyMedium)
        }

    }
}
