package com.example.chessclock.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Timer(onTimerSelected: (Any?, Any?) -> Unit) {
    var expanded by remember { mutableStateOf(true) }
    val timerOptions = listOf("1 minute", "5 minutes", "10 minutes", "30 minutes")
    var selectedTimer1Index by remember { mutableIntStateOf(0) }
    var selectedTimer2Index by remember { mutableIntStateOf(0) }

    val selectedTimer1 = timerOptions[selectedTimer1Index]
    val selectedTimer2 = timerOptions[selectedTimer2Index]

//    LaunchedEffect(selectedTimer1Index, selectedTimer2Index) {
//        onTimerSelected(selectedTimer1, selectedTimer2)
//    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = true },
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

@Preview
@Composable
fun TimerPreview() {
    Timer { value1, value2 ->
        print(value1)
        print(value2)
    }
}