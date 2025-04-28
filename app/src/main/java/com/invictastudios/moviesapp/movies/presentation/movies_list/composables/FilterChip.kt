package com.invictastudios.moviesapp.movies.presentation.movies_list.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xFF6200EE) else Color.LightGray,
        label = "BackgroundColorAnimation"
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) Color.White else Color.Black,
        label = "TextColorAnimation"
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        shadowElevation = 4.dp
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp
        )
    }
}