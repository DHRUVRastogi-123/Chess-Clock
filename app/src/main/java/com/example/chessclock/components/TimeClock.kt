package com.example.chessclock.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chessclock.R
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun TimeClock(initialTime1: String, initialTime2: String, increment: String) {
    var currentTime1 by remember { mutableStateOf(initialTime1) }
    var isTurn1 by remember { mutableStateOf(false) }
    var currentTime2 by remember { mutableStateOf(initialTime2) }
    var isTurn2 by remember { mutableStateOf(false) }
    var move1 by remember { mutableIntStateOf(0) }
    var move2 by remember { mutableIntStateOf(0) }
    var pauseOrPlay by remember { mutableIntStateOf(R.drawable.playclock) }
    var isPauseOrPlay by remember { mutableStateOf(false) }
    var prevTime1 by remember { mutableStateOf(initialTime1) }
    var prevTime2 by remember { mutableStateOf(initialTime2) }
    var isStarted by remember { mutableStateOf(false) }

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
                .height(screenHeight / 2 - 40.dp)
                .rotate(180F)
                .pointerInput(Unit) {
                    detectTapGestures {
                        if(pauseOrPlay == R.drawable.pauseclock){
                            if (isStarted && pauseOrPlay == R.drawable.pauseclock) {
                                if (isTimeDifferenceFiveSeconds(currentTime1, prevTime1)) {
                                    currentTime1 = addTimes(currentTime1, increment)
                                }
                            }
                        }
                        if(!isStarted) {
                            isStarted = true
                            pauseOrPlay = R.drawable.pauseclock
                        }
                        if (pauseOrPlay == R.drawable.pauseclock) {
                            isTurn1 = false
                            isTurn2 = true
                            move1 += 1
                            prevTime2 = currentTime2
                        }
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
                .height(80.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.strip),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                Surface(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                if (pauseOrPlay == R.drawable.pauseclock) {
                                    pauseOrPlay = R.drawable.playclock
                                    if (isTurn1) {
                                        isTurn1 = false
                                        isPauseOrPlay = true
                                    } else {
                                        isTurn2 = false
                                        isPauseOrPlay = false
                                    }
                                } else {
                                    pauseOrPlay = R.drawable.pauseclock
                                    if (isPauseOrPlay) {
                                        isTurn1 = true
                                    } else {
                                        isTurn2 = true
                                    }
                                }
                            }
                        },
                    color = Color.Black
                ) {
                    Image(
                        painter = painterResource(id = pauseOrPlay),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color= Color.White),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Surface(
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)
                        .clickable {
                            currentTime1 = initialTime1
                            currentTime2 = initialTime2
                            move1 = 0
                            move2 = 0
                            isPauseOrPlay = true
                            isStarted = false
                        },
                    color = Color.Black
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.reloadclock),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color= Color.White)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2 - 40.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (pauseOrPlay == R.drawable.pauseclock) {
                            if (isStarted) {
                                if (isTimeDifferenceFiveSeconds(currentTime2, prevTime2)) {
                                    currentTime2 = addTimes(currentTime2, increment)
                                }
                            }
                        }
                        if(!isStarted) {
                            isStarted = true
                            pauseOrPlay = R.drawable.pauseclock
                        }
                        if (pauseOrPlay == R.drawable.pauseclock) {
                            isTurn2 = false
                            isTurn1 = true
                            move2 += 1
                            prevTime1 = currentTime1
                        }
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

fun isTimeDifferenceFiveSeconds(time1: String, time2: String): Boolean {
    // Split the time strings into minutes and seconds
    val (min1, sec1) = time1.split(":").map { it.toInt() }
    val (min2, sec2) = time2.split(":").map { it.toInt() }

    // Calculate the total number of seconds for each time
    val totalSeconds1 = min1 * 60 + sec1
    val totalSeconds2 = min2 * 60 + sec2

    // Calculate the absolute difference in seconds between the two times
    val difference = abs(totalSeconds1 - totalSeconds2)

    // Check if the difference is exactly 5 seconds
    return difference <= 5
}

fun addTimes(time1: String, time2: String): String {
    // Split the time strings into minutes and seconds
    val (min1, sec1) = time1.split(":").map { it.toInt() }
    val (min2, sec2) = time2.split(":").map { it.toInt() }

    // Calculate the total number of seconds for each time
    val totalSeconds1 = min1 * 60 + sec1
    val totalSeconds2 = min2 * 60 + sec2

    // Add the total number of seconds together
    val totalSeconds = totalSeconds1 + totalSeconds2

    // Calculate the new minutes and seconds
    val newMin = totalSeconds / 60
    val newSec = totalSeconds % 60

    // Format the new time string with leading zeros if necessary
    val formattedMin = "%02d".format(newMin)
    val formattedSec = "%02d".format(newSec)

    return "$formattedMin:$formattedSec"
}


@Preview
@Composable
fun Call() {
    TimeClock(
        initialTime1 = "00:10",
        initialTime2 = "00:10",
        increment = "00:02"
    )
}
