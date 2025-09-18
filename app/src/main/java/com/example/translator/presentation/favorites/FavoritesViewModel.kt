package com.example.translator.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator.R
import com.example.translator.domain.usecase.AddTranslationToFavoritesUseCase
import com.example.translator.domain.usecase.ObserveFavoritesUseCase
import com.example.translator.domain.usecase.RemoveTranslationFromFavoritesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val addTranslationToFavoritesUseCase: AddTranslationToFavoritesUseCase,
    private val removeTranslationFromFavoritesByIdUseCase: RemoveTranslationFromFavoritesByIdUseCase,
    private val observeFavoritesUseCase: ObserveFavoritesUseCase
): ViewModel() {

    private var _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    private val _effects = Channel<FavoritesEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        observeFavorites()
    }

    fun handleIntent(intent: FavoritesIntent) {
        when(intent) {
            is FavoritesIntent.RemoveTranslationFromFavorites -> {
                removeTranslationFromFavorites(intent.id)
            }
        }
    }

    private fun removeTranslationFromFavorites(id: Long) {
        viewModelScope.launch {
            val messageRes = removeTranslationFromFavoritesByIdUseCase(id)
                .fold(
                    onSuccess = { R.string.success_removing_element_from_favorites },
                    onFailure = { R.string.unable_to_remove_element_from_favorites }
                )

            _effects.send(FavoritesEffect.ShowMessage(messageRes))
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            observeFavoritesUseCase().collect { favorites ->
                _uiState.update { currentState ->
                    currentState.copy(
                        favorites = favorites.map { it.toUiModel() }
                    )
                }
            }
        }
    }
}