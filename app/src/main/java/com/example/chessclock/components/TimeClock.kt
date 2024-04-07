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
fun TimeClock(initialTime: String, turn: Boolean, setTurn: (Boolean) -> Unit, setTime: (String) -> Unit, setTurnOther: (Boolean) -> Unit) {
    var currentTime by remember { mutableStateOf(initialTime) }
    var isTurn by remember { mutableStateOf(turn) }

    LaunchedEffect(isTurn) {
        while (isTurn) {
            delay(1000) // Decrease time every second
            val timeParts = currentTime.split(":")
            var minutes = timeParts[0].toInt()
            var seconds = timeParts[1].toInt()
            if (seconds == 0) {
                minutes -= 1
                seconds = 60
            }
            val newTime = String.format("%02d:%02d", minutes, seconds - 1)
            currentTime = newTime
            if (newTime == "00:00") {
                isTurn = false
                currentTime = "Time Up!"
            }
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2)
            .pointerInput(Unit) {
                detectTapGestures {
                    isTurn = !isTurn
                    setTurn(isTurn)
                    setTurnOther(!isTurn)
                    setTime(currentTime)
                }
            }
    ) {
        Text(
            text = currentTime,
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

@Preview
@Composable
fun Call(){
    val (turn1, setTurn1) = remember { mutableStateOf(true) }
    val (turn2, setTurn2) = remember { mutableStateOf(false) }
    val (time1, setTime1) = remember { mutableStateOf("00:15") }
    val (time2, setTime2) = remember { mutableStateOf("00:15") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TimeClock(initialTime = time1, turn = turn1, setTurn = setTurn1, setTime = setTime1, setTurnOther = setTurn2)
        TimeClock(initialTime = time2, turn = turn2, setTurn = setTurn2, setTime = setTime2, setTurnOther = setTurn1)
    }
}
