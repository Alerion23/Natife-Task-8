package com.example.natifetask8

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class NumberGenerator {

    private val startValue = 1
    private val endValue = 20

    private val _liveDataValue = MutableLiveData<Int>()
    val liveDataValue: LiveData<Int> = _liveDataValue

    private val _flowValue = MutableStateFlow<Int>(0)
    val flowValue = _flowValue.asStateFlow()


    fun liveDataCase() {
        Thread {
            for (i in 0..10) {
                _liveDataValue.postValue(generateNumber())
                Thread.sleep(2000)
            }
        }.start()
    }

    fun coroutineCase() {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..10) {
                _flowValue.emit(generateNumber())
                delay(2000)
            }
        }
    }

    fun rxCase(): Observable<Int> {
        return Observable.create {
            for (i in 0..10) {
                it.onNext(generateNumber())
                Thread.sleep(2000)
            }
            it.onComplete()
        }
    }

    private fun generateNumber(): Int {
        require(startValue <= endValue) { "Illegal Argument" }
        val rand = Random(System.nanoTime())
        return (startValue..endValue).random(rand)
    }
}