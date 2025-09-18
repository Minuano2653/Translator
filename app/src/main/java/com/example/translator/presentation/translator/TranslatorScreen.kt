package com.example.translator.presentation.translator

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.translator.R
import com.example.translator.presentation.translator.components.TranslationField
import com.example.translator.presentation.translator.components.TranslationHistoryHeader
import com.example.translator.presentation.models.TranslationUiModel
import com.example.translator.ui.components.BottomSheetAction
import com.example.translator.ui.components.TranslationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorScreen(
    modifier: Modifier = Modifier,
    onFavoritesClick: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: TranslatorViewModel = hiltViewModel<TranslatorViewModel>(),
    context: Context = LocalContext.current
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_bookmarks_24),
                            contentDescription = stringResource(R.string.favorites)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TranslationScreenContent(
            modifier = modifier.padding(paddingValues),
            originalText = uiState.originalText,
            translatedText = uiState.translatedText,
            isLoadingTranslation = uiState.isLoadingTranslation,
            translationHistory = uiState.translationHistory,
            onClearHistoryClick = { viewModel.handleIntent(TranslatorIntent.RemoveAllHistory) },
            onTranslationActionsClick = { translationUiModel ->
                viewModel.handleIntent(
                    TranslatorIntent.ShowTranslationHistoryActions(translationUiModel)
                )
            },
            onTextChange = { newText ->
                viewModel.handleIntent(TranslatorIntent.ChangeValue(newText))
            },
            onClearTranslationField = {
                viewModel.handleIntent(TranslatorIntent.ClearTranslationField)
            },
            onForceTranslate = {
                viewModel.handleIntent(TranslatorIntent.ForceTranslate)
            }
        )

        LaunchedEffect(Unit) {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is TranslatorEffect.ShowMessage -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(effect.message),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

        uiState.selectedItem?.let { selected ->
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.handleIntent(TranslatorIntent.HideTranslationHistoryActions)
                },
                containerColor = Color.White
            ) {
                val (labelRes, iconRes, favoriteIntent) = if (selected.isFavorite) {
                    Triple(
                        R.string.remove_from_favorites,
                        R.drawable.filled_bookmark_24,
                        TranslatorIntent.RemoveTranslationFromFavorites(selected)
                    )
                } else {
                    Triple(
                        R.string.add_to_favorites,
                        R.drawable.outline_bookmark_24,
                        TranslatorIntent.AddTranslationToFavorites(selected)
                    )
                }

                BottomSheetAction(
                    actionLabel = labelRes,
                    actionIconRes = iconRes,
                    onClick = { viewModel.handleIntent(favoriteIntent) }
                )
                BottomSheetAction(
                    actionLabel = R.string.remove_from_history,
                    actionIconRes = R.drawable.outline_delete_24,
                    onClick = {
                        viewModel.handleIntent(
                            TranslatorIntent.RemoveTranslationFromHistory(selected)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TranslationScreenContent(
    modifier: Modifier = Modifier,
    originalText: String,
    translatedText: String,
    isLoadingTranslation: Boolean,
    onTextChange: (String) -> Unit,
    translationHistory: List<TranslationUiModel>,
    onClearTranslationField: () -> Unit,
    onClearHistoryClick: () -> Unit,
    onTranslationActionsClick: (TranslationUiModel) -> Unit,
    onForceTranslate: () -> Unit
) {
    val lastItemShape = remember {
        RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
    }
    val regularItemShape = remember {
        RoundedCornerShape(0.dp)
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            TranslationField(
                originalText = originalText,
                translatedText = translatedText,
                isLoading = isLoadingTranslation,
                onClearText = onClearTranslationField,
                onValueChange = onTextChange,
                onTranslateClick = onForceTranslate
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            if (translationHistory.isNotEmpty()) {
                TranslationHistoryHeader(
                    onClearHistoryClick = onClearHistoryClick
                )
            }
        }

        itemsIndexed(
            items = translationHistory,
            key = { _, translation ->
                translation.id
            }
        ) { index, translation ->
            Surface(
                shape = if (index == translationHistory.lastIndex) lastItemShape else regularItemShape,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                TranslationItem(
                    translation = translation,
                    onTranslationActionsClick = { onTranslationActionsClick(translation) },
                    trailingIconRes = R.drawable.baseline_more_vert_24
                )
            }
        }
    }
}
