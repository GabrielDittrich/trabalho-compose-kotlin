import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.withContext

@Composable
fun FilmeDetailScreen(navController: NavHostController, filmeId: String?) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = FilmeDataBase.getDatabase(context)

    var filmeName by remember { mutableStateOf("") }
    var filmeAno by remember { mutableStateOf("") }

    val isEdit = filmeId != "null"

    // Agora usando Flow com collectAsState
    val filmeFlow = db.filmeDao().getFilmeById(filmeId?.toInt() ?: -1)
    val filme by filmeFlow.collectAsState(initial = null)

    // Quando o filme for carregado, atualize as variáveis de estado
    LaunchedEffect(filme) {
        filme?.let {
            filmeName = it.nome
            filmeAno = it.ano
        }
    }

    // Usando um ScrollableColumn para lidar com dispositivos com telas pequenas
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),  // Permite rolar quando o conteúdo for grande
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título da tela (Editar ou Novo)
        Text(
            text = if (isEdit) "Editar Filme" else "Novo Filme",
            style = MaterialTheme.typography.headlineSmall
        )

        // Campo de entrada para o nome do filme
        TextField(
            value = filmeName,
            onValueChange = { filmeName = it },
            placeholder = { Text("Nome do Filme") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Campo de entrada para o ano do filme
        TextField(
            value = filmeAno,
            onValueChange = { filmeAno = it },
            placeholder = { Text("Ano do Filme") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Botão para salvar ou adicionar filme
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    // Operações de banco de dados em background
                    if (isEdit) {
                        db.filmeDao().atualizarFilme(Filme(id = filmeId?.toInt() ?: 0, nome = filmeName, ano = filmeAno))
                    } else {
                        db.filmeDao().addFilme(Filme(nome = filmeName, ano = filmeAno))
                    }

                    // Navegação deve ser feita no thread principal
                    withContext(Dispatchers.Main) {
                        navController.navigate("taskList") {
                            popUpTo("taskList") { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(if (isEdit) "Salvar Alterações" else "Adicionar Filme")
        }
    }
}
