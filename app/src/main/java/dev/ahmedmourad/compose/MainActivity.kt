package dev.ahmedmourad.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import dev.ahmedmourad.compose.millions.*
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class MainActivity : AppCompatActivity() {
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var now = 0
            val clock by ticker(
                1000,
                0,
                Dispatchers.Default
            ).consumeAsFlow().map {
                createClock(Duration(now++))
            }.onStart {
                emit(createClock(null))
                delay(3000)
                emit(createClock(Duration(0)))
                delay(2000)
            }.collectAsState(initial = createClock(null))
            ComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Clock(clock = clock)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        createClock(null)
    }
}