package com.example.translator.presentation.translator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.R
import com.example.translator.domain.usecase.AddTranslationToFavoritesUseCase
import com.example.translator.domain.usecase.GetWordTranslationUseCase
import com.example.translator.domain.usecase.ObserveTranslationHistoryUseCase
import com.example.translator.domain.usecase.RemoveAllHistoryUseCase
import com.example.translator.domain.usecase.RemoveTranslationFromFavoritesByIdUseCase
import com.example.translator.domain.usecase.RemoveTranslationFromHistoryByIdUseCase
import com.example.translator.presentation.models.TranslationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val getWordTranslationUseCase: GetWordTranslationUseCase,
    private val observeTranslationHistoryUseCase: ObserveTranslationHistoryUseCase,
    private val removeAllHistoryUseCase: RemoveAllHistoryUseCase,
    private val translationFromHistoryByIdUseCase: RemoveTranslationFromHistoryByIdUseCase,
    private val addTranslationToFavoritesUseCase: AddTranslationToFavoritesUseCase,
    private val removeTranslationFromFavoritesByIdUseCase: RemoveTranslationFromFavoritesByIdUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(TranslatorUiState())
    val uiState: StateFlow<TranslatorUiState> = _uiState.asStateFlow()

    private val _effects = Channel<TranslatorEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()


    init {
        observeTextChangesAndTranslate()
        observeHistory()
    }

    fun handleIntent(intent: TranslatorIntent) {
        when (intent) {
            is TranslatorIntent.ChangeValue -> {
                onTextChanged(intent.newText)
            }
            is TranslatorIntent.ClearTranslationField -> {
                onClearTranslationField()
            }
            is TranslatorIntent.ShowTranslationHistoryActions -> {
                showTranslationHistoryActionsVisible(intent.translation)
            }
            is TranslatorIntent.HideTranslationHistoryActions -> {
                hideTranslationHistoryActionsVisible()
            }
            is TranslatorIntent.AddTranslationToFavorites -> {
                addTranslationToFavorites(intent.selectedItem)
            }
            is TranslatorIntent.RemoveTranslationFromFavorites -> {
                removeTranslationFromFavorites(intent.selectedItem.id)
            }
            is TranslatorIntent.RemoveTranslationFromHistory -> {
                removeTranslationFromHistory(intent.selectedItem.id)
            }
            is TranslatorIntent.RemoveAllHistory -> {
                removeAllHistory()
            }
            is TranslatorIntent.ForceTranslate -> {
                forceTranslate()
            }
        }
    }

    private fun forceTranslate() {
        val currentText = _uiState.value.originalText
        if (currentText.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingTranslation = true) }

            getWordTranslationUseCase(currentText).fold(
                onSuccess = { result ->
                    _uiState.update {
                        it.copy(
                            isLoadingTranslation = false,
                            translatedText = result.translation
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoadingTranslation = false)
                    }
                    val message = mapErrorToMessage(error)
                    _effects.send(TranslatorEffect.ShowMessage(message))
                }
            )
        }
    }


    private fun addTranslationToFavorites(selectedItem: TranslationUiModel) {
        viewModelScope.launch {
            addTranslationToFavoritesUseCase(selectedItem.toFavoriteDomain())
        }
        hideTranslationHistoryActionsVisible()
    }

    private fun removeTranslationFromFavorites(id: Long) {
        viewModelScope.launch {
            val messageRes = removeTranslationFromFavoritesByIdUseCase(id)
                .fold(
                    onSuccess = { R.string.success_removing_element_from_favorites },
                    onFailure = { R.string.unable_to_remove_element_from_favorites }
                )

            hideTranslationHistoryActionsVisible()
            _effects.send(TranslatorEffect.ShowMessage(messageRes))
        }
    }

    private fun removeTranslationFromHistory(id: Long) {
        viewModelScope.launch {
            val messageRes = translationFromHistoryByIdUseCase(id)
                .fold(
                    onSuccess = { R.string.success_removing_element_from_history },
                    onFailure = { R.string.unable_to_delete_element_from_history }
                )

            hideTranslationHistoryActionsVisible()
            _effects.send(TranslatorEffect.ShowMessage(messageRes))
        }
    }

    private fun removeAllHistory() {
        viewModelScope.launch {
            val messageRes = removeAllHistoryUseCase()
                .fold(
                    onSuccess = { R.string.success_clear_history },
                    onFailure = { R.string.unable_to_clear_history }
                )

            hideTranslationHistoryActionsVisible()
            _effects.send(TranslatorEffect.ShowMessage(messageRes))
        }
    }


    private fun onClearTranslationField() {
        _uiState.update { it.copy(originalText = "", translatedText = "") }
    }

    private fun onTextChanged(newText: String) {
        _uiState.update { it.copy(originalText = newText) }
    }

    private fun showTranslationHistoryActionsVisible(selectedItem: TranslationUiModel) {
        _uiState.update { it.copy(selectedItem = selectedItem) }
    }

    private fun hideTranslationHistoryActionsVisible() {
        _uiState.update { it.copy(selectedItem = null) }
    }

    @OptIn(FlowPreview::class)
    private fun observeTextChangesAndTranslate() {
        viewModelScope.launch {
            uiState
                .map { it.originalText }
                .distinctUntilChanged()
                .debounce(1000L)
                .collect { text ->
                    if (text.isBlank()) {
                        _uiState.update { it.copy(translatedText = "") }
                        return@collect
                    }

                    _uiState.update { it.copy(isLoadingTranslation = true) }

                    getWordTranslationUseCase(text.trimEnd()).fold(
                        onSuccess = { result ->
                            _uiState.update {
                                it.copy(
                                    isLoadingTranslation = false,
                                    translatedText = result.translation
                                )
                            }
                        },
                        onFailure = { error ->
                            _uiState.update {
                                it.copy(isLoadingTranslation = false)
                            }
                            val message = mapErrorToMessage(error)
                            _effects.send(TranslatorEffect.ShowMessage(message))
                        }
                    )

                }

        }
    }

    private fun observeHistory() {
        viewModelScope.launch {
            observeTranslationHistoryUseCase()
                .collect { historyList ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoadingHistory = false,
                            translationHistory = historyList.map { it.toUiModel() }
                        )
                    }
                }
        }
    }

    private fun mapErrorToMessage(error: Throwable?): Int {
        return when (error) {
            is UnknownHostException -> R.string.no_network_connection
            is SocketTimeoutException -> R.string.response_timeout
            else -> {
                R.string.unknown_error
            }
        }
    }
}