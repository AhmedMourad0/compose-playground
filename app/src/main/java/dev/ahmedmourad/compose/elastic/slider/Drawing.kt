package dev.ahmedmourad.compose.elastic.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import dev.ahmedmourad.compose.millions.Clock
import dev.ahmedmourad.compose.millions.createClock
import dev.ahmedmourad.compose.millions.rad
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ElasticRangeBar(
    min: Int,
    max: Int,
    range: IntRange,
    modifier: Modifier = Modifier,
    steps: Iterable<Int> = listOf(range.first, range.last),
    minThickness: Dp = 8.dp,
    hintThickness: Dp = 1.dp
) {
    val path = remember { Path() }
    Canvas(modifier = modifier) {
        val triggerRadius = size.height.dp / 2
        val startOffset = ((min - range.first) / (range.last - range.first)) * size.width
        val endOffset = ((max - range.first) / (range.last - range.first)) * size.width
        drawLine(
            Color.Gray,
            center.copy(x = 0f),
            center.copy(x = size.width),
            hintThickness.value
        )
        drawCircle(
            Color.Blue,
            triggerRadius.value,
            center.copy(x = triggerRadius.value)
        )
        drawCircle(
            Color.Blue,
            triggerRadius.value,
            center.copy(x = size.width - triggerRadius.value)
        )
        path.apply {
            reset()
            moveTo(triggerRadius.value, 0f)
            cubicTo(
                triggerRadius.value * 2,
                0f,
                triggerRadius.value,
                center.y - minThickness.value / 2,
                center.x,
                center.y - minThickness.value / 2
            )
            cubicTo(
                size.width - triggerRadius.value,
                center.y - minThickness.value / 2,
                size.width - triggerRadius.value * 2,
                0f,
                size.width - triggerRadius.value,
                0f
            )
            lineTo(
                size.width - triggerRadius.value,
                size.height
            )
            cubicTo(
                size.width - triggerRadius.value * 2,
                size.height,
                size.width - triggerRadius.value,
                center.y + minThickness.value / 2,
                center.x,
                center.y + minThickness.value / 2
            )
            cubicTo(
                triggerRadius.value,
                center.y + minThickness.value / 2,
                triggerRadius.value * 2,
                size.height,
                triggerRadius.value,
                size.height
            )
            close()
        }
        drawPath(path, Color.Blue)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 200,
    heightDp = 65
)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        ElasticRangeBar(
            min = 25,
            max = 75,
            range = 0..100,
            steps = listOf(0, 10, 20, 30, 40, 50, 100),
            hintThickness = 1.dp,
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp, 24.dp)
        )
    }
}
