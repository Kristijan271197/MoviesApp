package com.invictastudios.moviesapp.movies.presentation.search_movies_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    colors: TextFieldColors,
    text: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = "Search for a movie",
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(25.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            colors = colors,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "",
                    tint = Color.LightGray,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = text.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "",
                        tint = Color.LightGray,
                        modifier = Modifier
                            .clickable { onValueChange("") }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .background(Color.Transparent),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    defaultKeyboardAction(ImeAction.Done)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
        )
    }
}
