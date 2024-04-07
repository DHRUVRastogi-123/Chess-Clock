package com.example.chessclock.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TimeClock(initialTime1: String, initialTime2: String) {
    var currentTime1 by remember { mutableStateOf(initialTime1) }
    var isTurn1 by remember { mutableStateOf(true) }
    var currentTime2 by remember { mutableStateOf(initialTime2) }
    var isTurn2 by remember { mutableStateOf(false) }

    LaunchedEffect(isTurn1) {
        while (isTurn1) {
            if(currentTime1 == "Time's Up!" || currentTime2 == "Time's Up!"){
                isTurn1 = false
            }
            currentTime1 = updateTimer(currentTime1)
            print(5)
        }
    }

    LaunchedEffect(isTurn2) {
        while (isTurn2) {
            if(currentTime2 == "Time's Up!" || currentTime1 == "Time's Up!"){
                isTurn2 = false
            }
            currentTime2 = updateTimer(currentTime2)
            print(10)
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
                .pointerInput(Unit) {
                    detectTapGestures {
                        isTurn1 = false
                        isTurn2 = true
                    }
                }
        ) {
            Text(
                text = currentTime1,
                modifier = Modifier.fillMaxSize(),
                color = Color.Gray,
                fontSize = 88.sp,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2)
                .pointerInput(Unit) {
                    detectTapGestures {
                        isTurn2 = false
                        isTurn1 = true
                    }
                }
        ) {
            Text(
                text = currentTime2,
                modifier = Modifier.fillMaxSize(),
                color = Color.Gray,
                fontSize = 88.sp,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )
        }
    }
}


suspend fun updateTimer(currentTime: String): String {
    delay(1000) // Decrease time every second
    val timeParts = currentTime.split(":")
    var minutes = timeParts[0].toInt()
    var seconds = timeParts[1].toInt()
    if (seconds == 0) {
        minutes -= 1
        seconds = 60
    }
    val newTime = String.format("%02d:%02d", minutes, seconds - 1)
    if (newTime == "00:00") {
        return "Time's Up!"
    }
    return newTime
}

@Preview
@Composable
fun Call() {
    TimeClock(
        initialTime1 = "00:10",
        initialTime2 = "00:10"
    )
}
