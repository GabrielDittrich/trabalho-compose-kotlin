package com.example.trabalhokotlincompose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.trabalhokotlincompose.data.Filme
import com.example.trabalhokotlincompose.data.FilmeDataBase
import kotlinx.coroutines.launch

@Composable
fun FilmeDetailScreen(navController: NavHostController, filmeId: String?) {

    // ~ TEM UM ERRO NESSA TELA ~
    // TASK = null

    val corroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = FilmeDataBase.getDatabase(context)

    val filme: LiveData<Filme> = db.filmeDao().getFilmeById(filmeId?.toInt() ?: -1)

    var filmeName by remember { mutableStateOf(filme.value?.nome ?: "?") }
    var isEdit = filmeId != "null"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (isEdit) "Editar Tarefa" else "Nova Tarefa", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = filmeName
            onValueChange = { filmeName = it },
            placeholder = { Text("Nome da Filme") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isEdit) {
                    corroutineScope.launch {
                        db.filmeDao().atualizarFilme(filme.value!!.copy(nome = filmeName))
                    }

                } else {
                    corroutineScope.launch {
                        db.filmeDao().addFilme(Filme(nome = filmeName))
                    }
                }
                navController.navigate("taskList") { popUpTo("taskList") { inclusive = true } }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEdit) "Salvar Alterações" else "Adicionar Filme")
        }
    }
}
