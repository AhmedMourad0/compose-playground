package dev.ahmedmourad.compose.elastic.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import dev.ahmedmourad.compose.ui.theme.Purple500

private enum class Head {
    LOWER, UPPER
}

@Composable
fun ElasticRangeBar(
    lower: Float,
    upper: Float,
    onLowerChanged: (Float) -> Unit,
    onUpperChanged: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>,
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

    var size by remember { mutableStateOf(Size(0f, 0f)) }
    val center by rememberUpdatedState(size.center)

    val headRadius by rememberUpdatedState(size.height / 2)
    val lineWidth by rememberUpdatedState(size.width - headRadius * 2)
    val lowerOffset by rememberUpdatedState(
        headRadius + (lowerValue - range.first) / (range.last - range.first) * lineWidth
    )
    val upperOffset by rememberUpdatedState(
        headRadius + (upperValue - range.first) / (range.last - range.first) * lineWidth
    )
    val centerX by rememberUpdatedState((upperOffset + lowerOffset) / 2)
    val consumedRatio by rememberUpdatedState((upperOffset - lowerOffset) / lineWidth)
    val thickness by rememberUpdatedState(
        minBarThickness.value + (1 - consumedRatio) * (headRadius * 2 - minBarThickness.value)
    )

    val path = remember { Path() }
    val leftArcRect by rememberUpdatedState(Rect(Offset(lowerOffset, center.y), headRadius))
    val rightArcRect by rememberUpdatedState(Rect(Offset(upperOffset, center.y), headRadius))

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
        size = this.size
        drawLine(
            lineColor,
            center.copy(x = headRadius),
            center.copy(x = size.width - headRadius),
            lineThickness.value
        )
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
