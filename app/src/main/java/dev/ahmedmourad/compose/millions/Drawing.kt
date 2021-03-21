package dev.ahmedmourad.compose.millions

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(modifier: Modifier = Modifier, clock: Clock) {
    val spacing = 0f
    val columnsCount = clock.digits.rows.first().fragments.size
    val rowsCount = clock.digits.rows.size
    val accHorizontalSpacing = spacing * (columnsCount - 1)
    val accVerticalSpacing = spacing * (rowsCount - 1)
    fun findDiameter(size: Size): Float {
        val candidateWidth = (size.width - accHorizontalSpacing) / columnsCount
        val candidateHeight = (size.height - accVerticalSpacing) / rowsCount
        return minOf(candidateWidth, candidateHeight)
    }
    Column {
        clock.digits.rows.forEachIndexed { rowIndex, row ->
            Row {
                row.fragments.forEachIndexed { columnIndex, fragment ->
                    fun findCenterAndRadius(size: Size): Pair<Offset, Float> {
                        val diameter = findDiameter(size)
                        val radius = diameter / 2
                        return Offset(
                            columnIndex * diameter + columnIndex * spacing + radius,
                            rowIndex * diameter + rowIndex * spacing + radius
                        ) to radius
                    }
                    DigitFragment(modifier,  fragment) {
                        findCenterAndRadius(it)
                    }
                }
            }
        }
    }
}

@Composable
fun DigitFragment(
    modifier: Modifier = Modifier,
    fragment: DigitFragment,
    findCenterAndRadius: (Size) -> Pair<Offset, Float>
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val (center, radius) = findCenterAndRadius(size)
        drawCircle(Color.Gray, radius, center, style = Stroke(2f))
        val newX1 = center.x + radius * cos(fragment.firstAngle.unaryMinus().toFloat().rad())
        val newY1 = center.y + radius * sin(fragment.firstAngle.unaryMinus().toFloat().rad())
        drawLine(Color.Black, center, Offset(newX1, newY1), 3f)
        val newX2 = center.x + radius * cos(fragment.secondAngle.unaryMinus().toFloat().rad())
        val newY2 = center.y + radius * sin(fragment.secondAngle.unaryMinus().toFloat().rad())
        drawLine(Color.Black, center, Offset(newX2, newY2), 3f)
    }
}

fun Float.rad() = this / 180 * PI.toFloat()

fun createClock(duration: Duration?): Clock {
    duration ?: return Clock(Digits.from(null), Digits.from(null), Digits.from(null), Digits.from(null))
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

@Composable
private fun getPaintFromCache(): Paint {
    return remember {
        Paint().apply {
            strokeCap = StrokeCap.Round
            style = PaintingStyle.Stroke
            strokeWidth = 8.0f
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Clock(clock = createClock(null))
    }
}
