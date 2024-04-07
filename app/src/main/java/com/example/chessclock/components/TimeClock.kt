package com.example.chessclock.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimeClock(time: String, turn: Boolean) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2)
    ) {
        Text(
            text = time,
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
        )
    }
}

@Preview
@Composable
fun Call(){
    TimeClock(time = "15:00", turn = true)
}

