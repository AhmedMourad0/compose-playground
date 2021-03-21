package dev.ahmedmourad.compose.millions

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(modifier: Modifier = Modifier, clock: Clock) {
    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        clock.digits.rows.forEach { row ->
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                row.fragments.forEach { fragment ->
                    DigitFragment(Modifier.weight(1f), fragment)
                }
            }
        }
    }
}

@Composable
fun DigitFragment(
    modifier: Modifier = Modifier,
    fragment: DigitFragment
) {
    val angle1 by animateFloatAsState(
        targetValue = fragment.firstAngle.unaryMinus().toFloat().rad(),
        animationSpec = tween(700)
    )
    val angle2 by animateFloatAsState(
        targetValue = fragment.secondAngle.unaryMinus().toFloat().rad(),
        animationSpec = tween(700)
    )
    Canvas(modifier = modifier.aspectRatio(1f)) {
        val center = size.center
        val radius = size.minDimension / 2
        drawCircle(Color.Gray, radius, center, style = Stroke(2f))
        val newX1 = center.x + radius * cos(angle1)
        val newY1 = center.y + radius * sin(angle1)
        drawLine(Color.Black, center, Offset(newX1, newY1), 3f)
        val newX2 = center.x + radius * cos(angle2)
        val newY2 = center.y + radius * sin(angle2)
        drawLine(Color.Black, center, Offset(newX2, newY2), 3f)
    }
}

fun Float.rad() = this / 180 * PI.toFloat()

fun createClock(duration: Duration?): Clock {
    duration ?: return Clock(
        Digits.from(null),
        Digits.from(null),
        Digits.from(null),
        Digits.from(null)
    )
    val firstMinute = duration.minutes / 10
    val secondMinute = duration.minutes % 10
    val firstSecond = duration.seconds / 10
    val secondSecond = duration.seconds % 10
    return Clock(
        Digits.from(firstMinute),
        Digits.from(secondMinute),
        Digits.from(firstSecond),
        Digits.from(secondSecond)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Clock(clock = createClock(null))
    }
}
