package com.example.chessclock.Model

import android.os.CountDownTimer
//Main Kotlin file by Dhruv and Nabhanshu :)
//So we want to define everything here,i.e,startTimer,Pause,Restart etc.
class Timer(private val initialTime: Long) {
    private var currentTime = initialTime
    //Since timer is nullable, it can hold a CountDownTimer object if one is assigned to it later,
    // or it can remain null if no object is assigned.
    private var timer: CountDownTimer? = null

    fun startTimer(listener: TimerListener) {
        timer = object : CountDownTimer(currentTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime = millisUntilFinished
                listener.onTick(currentTime)
            }

            override fun onFinish() {
                listener.onFinish()
            }
        }
        timer?.start()
    }

    fun pauseTimer() {
        timer?.cancel()
    }

    fun resetTimer() {
        timer?.cancel()
        currentTime = initialTime
    }
}

/* TimerListener can perform actions in response to the timer finishing,
such as displaying a message or triggering another event. */
interface TimerListener {
    fun onTick(currentTime: Long)
    fun onFinish()
}