package com.example.chessclock.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessclock.R

@Composable
fun Timer(selectedTimer1Index: String, selectedTimer2Index: String,timerOptions: List<String>, onTimerSelected: (String, String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val timerOptions = listOf("1 minute", "5 minutes", "10 minutes", "30 minutes")
    var selectedTimer1Index by remember { mutableIntStateOf(0) }
    var selectedTimer2Index by remember { mutableIntStateOf(0) }

    val selectedTimer1 = timerOptions[selectedTimer1Index]
    val selectedTimer2 = timerOptions[selectedTimer2Index]

    LaunchedEffect(selectedTimer1Index, selectedTimer2Index) {
        onTimerSelected(selectedTimer1, selectedTimer2)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.padding(16.dp)
        ) {
            timerOptions.forEachIndexed { index, timer ->
                DropdownMenuItem(
                    text = {
                           Text(
                               text = timer
                           )
                    },
                    onClick = {
                    selectedTimer1Index = index
                    selectedTimer2Index = index
                    expanded = false
                    onTimerSelected(timer, timer)
                })
            }
        }

        Text(
            text = "Initial Time for Player 1: $selectedTimer1",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            color = Color.Black
        )

        Text(
            text = "Initial Time for Player 2: $selectedTimer2",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}



//import android.os.CountDownTimer
////Main Kotlin file by Dhruv and Nabhanshu :)
////So we want to define everything here,i.e,startTimer,Pause,Restart etc.
//class Timer(private val initialTime: Long) {
//    private var currentTime = initialTime
//    //Since timer is nullable, it can hold a CountDownTimer object if one is assigned to it later,
//    // or it can remain null if no object is assigned.
//    private var timer: CountDownTimer? = null
//
//    fun startTimer(listener: TimerListener) {
//        timer = object : CountDownTimer(currentTime, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                currentTime = millisUntilFinished
//                listener.onTick(currentTime)
//            }
//
//            override fun onFinish() {
//                listener.onFinish()
//            }
//        }
//        timer?.start()
//    }
//
//    fun pauseTimer() {
//        timer?.cancel()
//    }
//
//    fun resetTimer() {
//        timer?.cancel()
//        currentTime = initialTime
//    }
//}
//
///* TimerListener can perform actions in response to the timer finishing,
//such as displaying a message or triggering another event. */
//interface TimerListener {
//    fun onTick(currentTime: Long)
//    fun onFinish()
//}