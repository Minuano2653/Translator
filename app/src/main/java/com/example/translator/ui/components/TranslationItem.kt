package com.example.translator.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.translator.presentation.models.TranslationUiModel

@Composable
fun TranslationItem(
    modifier: Modifier = Modifier,
    translation: TranslationUiModel,
    @DrawableRes trailingIconRes: Int,
    onTranslationActionsClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = translation.original,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp
                )
            )
        },
        supportingContent = {
            Text(
                text = translation.translated,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 16.sp)
            )
        },
        trailingContent = {
            IconButton(onClick = onTranslationActionsClick) {
                Icon(
                    painter = painterResource(trailingIconRes),
                    contentDescription = null
                )
            }
        },
        colors = ListItemDefaults.colors(containerColor = Color.White)
    )
}
