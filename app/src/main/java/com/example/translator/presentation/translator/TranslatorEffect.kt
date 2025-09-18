package com.example.translator.presentation.translator

sealed class TranslatorEffect {
    data class ShowMessage(val message: Int): TranslatorEffect()
}