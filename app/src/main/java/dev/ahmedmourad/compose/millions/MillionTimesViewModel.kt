package dev.ahmedmourad.compose.millions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan

class MillionTimesViewModel : ViewModel() {
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    val ticker = ticker(
        1000,
        0,
        Dispatchers.IO
    ).consumeAsFlow().scan(0) { acc, _ ->
        acc + 1
    }.map {
        createClock(Duration(it))
    }.onStart {
        emit(createClock(null))
        delay(3000)
        emit(createClock(Duration(0)))
        delay(2000)
    }
}
