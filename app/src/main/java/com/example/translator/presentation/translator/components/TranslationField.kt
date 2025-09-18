package com.example.translator.presentation.translator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.translator.R
import com.example.translator.ui.theme.TranslatorTheme

@Composable
fun TranslationField(
    originalText: String,
    translatedText: String,
    onClearText: () -> Unit,
    isLoading: Boolean,
    onValueChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.english),
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                painter = painterResource(R.drawable.arrow_right_24),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.russian),
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Spacer(modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(0.5.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = originalText,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = stringResource(R.string.enter_word))
            },
            trailingIcon = {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                    originalText.isNotEmpty() -> {
                        IconButton(onClick = onClearText) {
                            Icon(
                                painter = painterResource(R.drawable.outline_clear_24),
                                contentDescription = stringResource(R.string.clear_field)
                            )
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        if (translatedText.isNotEmpty()) {
            Spacer(modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(0.5.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                text = translatedText
            )
        }
    }
}

@Preview
@Composable
fun TranslationFieldPreview() {
    var text by remember { mutableStateOf("pneumonoultramicroscopicsilicovolcanoconiosis") }
    TranslatorTheme {
        TranslationField(
            originalText = text,
            translatedText = if (text == "pneumonoultramicroscopicsilicovolcanoconiosis") "пневмоноультрамикроскопический силикоз" else "",
            onValueChange = {
                text = it
            },
            onClearText = {
                text = ""
            },
            isLoading = true,
            onTranslateClick = {}
        )
    }
}