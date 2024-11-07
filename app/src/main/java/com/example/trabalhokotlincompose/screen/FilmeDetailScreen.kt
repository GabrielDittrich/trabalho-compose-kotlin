import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trabalhokotlincompose.data.Filme
import com.example.trabalhokotlincompose.data.FilmeDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FilmeDetailScreen(navController: NavHostController, filmeId: String?) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = FilmeDataBase.getDatabase(context)

    var filmeName by remember { mutableStateOf("") }
    var filmeAno by remember { mutableStateOf("") }

    val isEdit = filmeId != "null"

    LaunchedEffect(filmeId) {
        if (isEdit) {
            val filmeResult = db.filmeDao().getFilmeById(filmeId?.toInt() ?: -1)
            filmeResult.observeForever { filme ->
                filme?.let {
                    filmeName = it.nome
                    filmeAno = it.ano
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isEdit) "Editar Filme" else "Novo Filme",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = filmeName,
            onValueChange = { filmeName = it },
            placeholder = { Text("Nome do Filme") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = filmeAno,
            onValueChange = { filmeAno = it },
            placeholder = { Text("Ano do Filme") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    if (isEdit) {
                        db.filmeDao().atualizarFilme(Filme(id = filmeId?.toInt() ?: 0, nome = filmeName, ano = filmeAno))
                    } else {
                        db.filmeDao().addFilme(Filme(nome = filmeName, ano = filmeAno))
                    }
                    navController.navigate("taskList") {
                        popUpTo("taskList") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEdit) "Salvar Alterações" else "Adicionar Filme")
        }
    }
}
