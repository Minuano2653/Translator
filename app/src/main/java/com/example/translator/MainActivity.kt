package com.example.translator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.translator.navigation.AppNavGraph
import com.example.translator.navigation.Favorites
import com.example.translator.navigation.Translator
import com.example.translator.navigation.navigateToFavorites
import com.example.translator.ui.components.BottomSheetAction
import com.example.translator.ui.theme.TranslatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                TranslatorApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Box(modifier = modifier.fillMaxSize()) {
        AppNavGraph(
            modifier = modifier,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val historySheetState = rememberModalBottomSheetState()
    val actionsSheetState = rememberModalBottomSheetState()

    var showHistorySheet by remember { mutableStateOf(true) }
    var showActionsSheet by remember { mutableStateOf(false) }

    /*CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.favorites)) },
        navigationIcon = {
            IconButton(onClick = navController::popBackStack) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24),
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
    )*/

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
                .background(Color.Magenta)
                .clickable { showHistorySheet = true }
        )

        if (showHistorySheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showHistorySheet = false
                },
                sheetState = historySheetState
            ) {
                LazyColumn {
                    items(30) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clickable { showActionsSheet = true }
                                .padding(start = 16.dp),
                            contentAlignment = Alignment.Center// 👈 центр и по вертикали, и по горизонтали
                        ) {
                            Text(modifier = Modifier.fillMaxWidth(), text = "Жопа")
                        }
                    }
                }
            }
        }

        if (showActionsSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showHistorySheet = false
                },
                sheetState = actionsSheetState,
                containerColor = Color.White
            ) {
                BottomSheetAction(
                    actionLabel = R.string.add_to_favorites,
                    actionIconRes = R.drawable.outline_bookmark_24,
                    onClick = { showActionsSheet = false }
                )

                BottomSheetAction(
                    actionLabel = R.string.remove_from_history,
                    actionIconRes = R.drawable.outline_delete_24,
                    onClick = { showActionsSheet = false }
                )
            }
        }

    }
}