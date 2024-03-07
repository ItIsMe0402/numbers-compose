package com.github.itisme0402.numberscompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.itisme0402.numberscompose.core.NumberInfo
import com.github.itisme0402.numberscompose.ui.theme.NumbersComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumbersComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = DESTINATION_HOME,
                    ) {
                        composable(DESTINATION_HOME) {
                            val viewModel: HomeViewModel by viewModels()
                            HomeScreen(
                                viewModel = viewModel
                            ) { (number, fact) ->
                                navController.navigate("$DESTINATION_FACT/$number/$fact")
                            }
                        }
                        composable(
                            "$DESTINATION_FACT/{$NUMBER}/{$FACT}",
                            listOf(
                                navArgument(NUMBER) { type = NavType.IntType },
                                navArgument(FACT) { type = NavType.StringType },
                            ),
                        ) { backStackEntry ->
                            val arguments = backStackEntry.arguments!!
                            val number = arguments.getInt(NUMBER)
                            val fact = arguments.getString(FACT)!!
                            FactScreen(number, fact)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val DESTINATION_HOME = "home"
        const val DESTINATION_FACT = "info"
        const val NUMBER = "number"
        const val FACT = "fact"
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavToFact: (NumberInfo) -> Unit,
) {
    val isGetFactEnabled by viewModel.isGetFactEnabled.collectAsStateWithLifecycle()
    val history by viewModel.history.collectAsStateWithLifecycle(initialValue = emptyList())
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel.openFactScreenEvent) {
        viewModel.openFactScreenEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collect(onNavToFact)
    }
    HomeScreen(
        isGetFactEnabled,
        history,
        viewModel::onInputTextChanged,
        viewModel::onGetFactClicked,
        viewModel::onGetRandomFactClicked,
        viewModel::onHistoryItemClicked,
    )
}

@Composable
fun HomeScreen(
    isGetFactEnabled: Boolean = false,
    history: List<NumberInfo> = listOf(TEST_NUMBER_INFO),
    onInputTextChanged: (String) -> Unit = {},
    onGetFactClicked: (String) -> Unit = {},
    onGetRandomFactClicked: () -> Unit = {},
    onHistoryItemClicked: (NumberInfo) -> Unit = {},
) {
    var numberText by rememberSaveable {
        mutableStateOf("")
    }
    Column {
        TextField(
            value = numberText,
            onValueChange = { text ->
                numberText = text
                onInputTextChanged(numberText)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {
                onGetFactClicked(numberText)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isGetFactEnabled,
        ) {
            Text(text = "Get fact")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = onGetRandomFactClicked,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Get fact about random number")
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(history) { numberInfo ->
                Column(modifier = Modifier
                    .heightIn(min = 48.dp)
                    .clickable { onHistoryItemClicked(numberInfo) }) {
                    Text(
                        text = numberInfo.number.toString(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = numberInfo.fact,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() = HomeScreen()

@Composable
fun FactScreen(
    number: Int,
    fact: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "A fact for number $number:",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = fact,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun FactScreen() = FactScreen(TEST_NUMBER_INFO.number, TEST_NUMBER_INFO.fact)

val TEST_NUMBER_INFO = NumberInfo(
    10,
    "10 is the number of official inkblots in the Rorschach inkblot test."
)
