package com.example.translator.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.translator.R
import com.example.translator.ui.theme.TranslatorTheme


@Composable
fun BottomSheetAction(
    modifier: Modifier = Modifier,
    @StringRes actionLabel: Int,
    @DrawableRes actionIconRes: Int,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier
            .clickable { onClick() },
        leadingContent = {
            Icon(
                painter = painterResource(actionIconRes),
                contentDescription = null,
                tint = Color.Black
            )
        },
        headlineContent = {
            Text(text = stringResource(actionLabel))
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.White
        )
    )
}

@Preview
@Composable
fun BottomSheetActionAddToFavoritesPreview() {
    TranslatorTheme {
        BottomSheetAction(
            actionLabel = R.string.add_to_favorites,
            actionIconRes = R.drawable.outline_bookmark_24,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun BottomSheetActionRemoveFromFavoritesPreview() {
    TranslatorTheme {
        BottomSheetAction(
            actionLabel = R.string.remove_from_favorites,
            actionIconRes = R.drawable.filled_bookmark_24,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun BottomSheetActionRemoveFromHistoryPreview() {
    TranslatorTheme {
        BottomSheetAction(
            actionLabel = R.string.remove_from_history,
            actionIconRes = R.drawable.outline_delete_24,
            onClick = {}
        )
    }
}

