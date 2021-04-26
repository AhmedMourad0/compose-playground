package dev.ahmedmourad.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ahmedmourad.compose.elastic.slider.ElasticRangeBar
import dev.ahmedmourad.compose.millions.Clock
import dev.ahmedmourad.compose.millions.MillionTimesViewModel
import dev.ahmedmourad.compose.millions.createClock
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

class MainActivity : AppCompatActivity() {

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var lower by remember { mutableStateOf(25f) }
            var upper by remember { mutableStateOf(100f) }
            ComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ElasticRangeBar(
                        lower = lower,
                        upper = upper,
                        onLowerChanged = { lower = it },
                        onUpperChanged = { upper = it },
                        range = 25f..100f,
                        steps = listOf(0f, 10f, 20f, 30f, 40f, 50f, 100f),
                        lineThickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                    )
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Composable
    fun MillionTimes(
        modifier: Modifier = Modifier
    ) {
        val vm = remember { MillionTimesViewModel() }
        val clock by vm.ticker.collectAsState(createClock(null))
        Clock(modifier, clock)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        createClock(null)
    }
}