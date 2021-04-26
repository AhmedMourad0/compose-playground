package dev.ahmedmourad.compose.elastic.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import dev.ahmedmourad.compose.ui.theme.*

private enum class Head {
    LOWER, UPPER
}

@Composable
fun ElasticRangeBar(
    lower: Float,
    upper: Float,
    onLowerChanged: (Float) -> Unit,
    onUpperChanged: (Float) -> Unit,
    range: ClosedRange<Float>,
    modifier: Modifier = Modifier,
    steps: Iterable<Float> = listOf(range.first, range.last),
    minBarThickness: Dp = 6.dp,
    lineThickness: Dp = 1.dp,
    padding: PaddingValues = PaddingValues(16.dp, 32.dp),
    barColor: Color = Purple500,
    lineColor: Color = Color.Gray
) {
    val lowerValue by rememberUpdatedState(lower)
    val upperValue by rememberUpdatedState(upper)
    val path = remember { Path() }
    var lowerOffset by remember { mutableStateOf(0f) }
    var upperOffset by remember { mutableStateOf(0f) }
    var lineWidth by remember { mutableStateOf(0f) }
    var headRadius by remember { mutableStateOf(0f) }
    var headToMove by remember { mutableStateOf(Head.LOWER) }
    var switchHead by remember { mutableStateOf(true) }
    Canvas(modifier = modifier.pointerInput("Horizontal Drag Input") {
        detectHorizontalDragGestures(onDragStart = {
            switchHead = false
        }, onDragEnd = {
            switchHead = true
        }, onDragCancel = {
            switchHead = true
        }, onHorizontalDrag = { change, dragAmount ->
            change.consumeAllChanges()
            if (switchHead) {
                headToMove = when {
                    lowerValue == upperValue -> if (dragAmount > 0) Head.UPPER else Head.LOWER
                    change.previousPosition.x <= lowerOffset -> Head.LOWER
                    change.previousPosition.x >= upperOffset -> Head.UPPER
                    change.previousPosition.x - lowerOffset > upperOffset - change.previousPosition.x -> Head.UPPER
                    else -> Head.LOWER
                }
            }
            when (headToMove) {
                Head.LOWER -> {
                    val new = range.first +
                            (change.position.x - headRadius) / lineWidth * (range.last - range.first)
                    onLowerChanged(new.coerceIn(range.first, upperValue))
                }
                Head.UPPER -> {
                    val new = range.first +
                            (change.position.x - headRadius) / lineWidth * (range.last - range.first)
                    onUpperChanged(new.coerceIn(lowerValue, range.last))
                }
            }
        })
    }.pointerInput("Click Input") {
        detectTapGestures {
            when {
                it.x <= headRadius -> {
                    onLowerChanged(range.first)
                }
                it.x >= lineWidth + headRadius -> {
                    onUpperChanged(range.last)
                }
                (it.x <= lowerOffset) || it.x - lowerOffset < upperOffset - it.x -> {
                    val new = range.first +
                            (it.x - headRadius) / lineWidth * (range.last - range.first)
                    onLowerChanged(new)
                }
                else -> {
                    val new = range.first +
                            (it.x - headRadius) / lineWidth * (range.last - range.first)
                    onUpperChanged(new)
                }
            }
        }
    }.padding(padding)) {

        headRadius = size.height / 2
        lineWidth = size.width - headRadius * 2
        lowerOffset = headRadius +
                ((lowerValue - range.first) / (range.last - range.first)) * lineWidth
        upperOffset = headRadius +
                ((upperValue - range.first) / (range.last - range.first)) * lineWidth
        val centerX = (upperOffset + lowerOffset) / 2
        val consumedRatio = (upperOffset - lowerOffset) / lineWidth
        val thickness = minBarThickness.value +
                (1 - consumedRatio) * (headRadius * 2 - minBarThickness.value)
        drawLine(
            lineColor,
            center.copy(x = headRadius),
            center.copy(x = size.width - headRadius),
            lineThickness.value
        )
        val leftArcRect = Rect(Offset(lowerOffset, center.y), headRadius)
        val rightArcRect = Rect(Offset(upperOffset, center.y), headRadius)
        path.apply {
            reset()
            moveTo(lowerOffset, size.height)
            arcTo(leftArcRect, 90f, 180f, false)
            cubicTo(
                lowerOffset + headRadius,
                0f,
                lowerOffset,
                center.y - thickness / 2,
                centerX,
                center.y - thickness / 2
            )
            cubicTo(
                upperOffset,
                center.y - thickness / 2,
                upperOffset - headRadius,
                0f,
                upperOffset,
                0f
            )
            arcTo(rightArcRect, 270f, 180f, false)
            cubicTo(
                upperOffset - headRadius,
                size.height,
                upperOffset,
                center.y + thickness / 2,
                centerX,
                center.y + thickness / 2
            )
            cubicTo(
                lowerOffset,
                center.y + thickness / 2,
                lowerOffset + headRadius,
                size.height,
                lowerOffset,
                size.height
            )
            close()
        }
        drawPath(path, barColor)
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
            lower = 25f,
            upper = 75f,
            onLowerChanged = { },
            onUpperChanged = { },
            range = 0f..100f,
            steps = listOf(0f, 10f, 20f, 30f, 40f, 50f, 100f),
            lineThickness = 1.dp,
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp, 24.dp)
        )
    }
}

val <T : Comparable<T>> ClosedRange<T>.first
    get() = this.start

val <T : Comparable<T>> ClosedRange<T>.last
    get() = this.endInclusive
