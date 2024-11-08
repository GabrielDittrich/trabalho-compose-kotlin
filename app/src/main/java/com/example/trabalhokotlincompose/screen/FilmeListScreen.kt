package com.example.trabalhokotlincompose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.trabalhokotlincompose.data.Filme
import com.example.trabalhokotlincompose.data.FilmeDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FilmeListScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = FilmeDataBase.getDatabase(context)

    // Usando Flow e collectAsState para obter os filmes
    val filmesFlow = db.filmeDao().listarFilmes()
    val filmes by filmesFlow.collectAsState(initial = emptyList())  // Agora usamos collectAsState com Flow

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Meus Filmes", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Componente de input para adicionar filmes
        FilmeInputField { filmeNome ->
            coroutineScope.launch(Dispatchers.IO) {
                db.filmeDao().addFilme(Filme(nome = filmeNome, ano = "2024", assistido = false))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Passando os filmes para a lista
        FilmeList(filmes = filmes, coroutineScope = coroutineScope, db = db, navController = navController)
    }
}


@Composable
fun FilmeList(
    filmes: List<Filme>,
    coroutineScope: CoroutineScope,
    db: FilmeDataBase,
    navController: NavHostController
) {
    LazyColumn {
        items(filmes) { filme ->
            FilmeItem(
                filme = filme,
                onCheckedChange = { isChecked ->
                    coroutineScope.launch(Dispatchers.IO) {
                        db.filmeDao().atualizarFilme(filme.copy(assistido = isChecked))
                    }
                },
                onDelete = {
                    coroutineScope.launch(Dispatchers.IO) {
                        db.filmeDao().deletarFilme(filme)
                    }
                },
                onEditClick = {
                    navController.navigate("filmeDetail/${filme.id}")
                }
            )
        }
    }
}


@Composable
fun FilmeItem(
    filme: Filme,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = filme.assistido,
            onCheckedChange = { onCheckedChange(it) },
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = filme.nome,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            textDecoration = if (filme.assistido) TextDecoration.LineThrough else null
        )
        IconButton(onClick = onEditClick) {
            Icon(imageVector = Icons.Default.Build, contentDescription = "Editar Filme")
        }

        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Deletar Filme")
        }
    }
}

@Composable
fun FilmeInputField(onAddTask: (String) -> Unit) {
    var newFilme by remember { mutableStateOf("") }

    Column {
        TextField(
            value = newFilme,
            onValueChange = { newFilme = it },
            placeholder = { Text("Digite o filme") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (newFilme.isNotBlank()) {
                    onAddTask(newFilme)
                    newFilme = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar")
        }
    }
}
