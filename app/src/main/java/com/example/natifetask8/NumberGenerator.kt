package com.example.natifetask8

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class NumberGenerator {

    private val startValue = 1
    private val endValue = 20
    var thread: Thread? = null
        private set

    private val _liveDataValue = MutableLiveData<Int>()
    val liveDataValue: LiveData<Int> = _liveDataValue

    fun liveDataCase() {
        var isRunning = true
        thread = Thread {
            while (isRunning) {
                try {
                    for (i in 0..10) {
                        _liveDataValue.postValue(generateNumber())
                        Thread.sleep(2000)
                    }
                } catch (e: InterruptedException) {
                    isRunning = false
                }
            }
        }
        thread?.start()
    }

    fun coroutineCase(): Flow<Int> {
        return flow {
            for (i in 0..10) {
                emit(generateNumber())
                delay(2000)
            }
        }
    }

    fun rxCase(): Observable<Int> {
        return Observable.interval(0, 2, TimeUnit.SECONDS)
            .map { generateNumber() }
    }

    private fun generateNumber(): Int {
        require(startValue <= endValue) { "Illegal Argument" }
        val rand = Random(System.nanoTime())
        return (startValue..endValue).random(rand)
    }
}