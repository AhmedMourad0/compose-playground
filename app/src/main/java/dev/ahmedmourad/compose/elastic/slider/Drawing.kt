package dev.ahmedmourad.compose.elastic.slider

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.ahmedmourad.compose.ui.theme.ComposeTheme
import kotlin.math.roundToInt

private enum class Head {
    LEFT, RIGHT
}

@Composable
fun ElasticRangeBar(
    v: State<ClosedRange<Float>>,
    onMinChanged: (Float) -> Unit,
    onMaxChanged: (Float) -> Unit,
    range: ClosedRange<Float>,
    modifier: Modifier = Modifier,
    steps: Iterable<Float> = listOf(range.first, range.last),
    minThickness: Dp = 8.dp,
    hintThickness: Dp = 1.dp,
    padding: PaddingValues = PaddingValues(16.dp, 32.dp)
) {
    val value by remember { v }
    val path = remember { Path() }
    var startOffset by remember { mutableStateOf(0f) }
    var endOffset by remember { mutableStateOf(0f) }
    var hintWidth by remember { mutableStateOf(0f) }
    var triggerRadius by remember { mutableStateOf(0f) }
    var headToMove by remember { mutableStateOf(Head.LEFT) }
    var switchHead by remember { mutableStateOf(true) }
    Canvas(modifier = modifier.pointerInput("Horizontal Drag Input") {
        detectHorizontalDragGestures(
            onDragStart = {
                switchHead = false
            }, onDragEnd = {
                switchHead = true
            }, onDragCancel = {
                switchHead = true
            }, onHorizontalDrag = { change, dragAmount ->
                change.consumeAllChanges()
                if (switchHead) {
                    headToMove = when {
                        value.first == value.last -> if (dragAmount > 0) Head.RIGHT else Head.LEFT
                        change.previousPosition.x <= startOffset -> Head.LEFT
                        change.previousPosition.x >= endOffset -> Head.RIGHT
                        change.previousPosition.x - startOffset > endOffset - change.previousPosition.x -> Head.RIGHT
                        else -> Head.LEFT
                    }
                }
                when (headToMove) {
                    Head.LEFT -> {
                        val new = range.first +
                                (change.position.x - triggerRadius) / hintWidth * (range.last - range.first)
                        onMinChanged(new.coerceIn(range.first, value.last))
                    }
                    Head.RIGHT -> {
                        val new = range.first +
                                (change.position.x - triggerRadius) / hintWidth * (range.last - range.first)
                        onMaxChanged(new.coerceIn(value.first, range.last))
                    }
                }
            })
    }.pointerInput("Click Input") {
        detectTapGestures {
            when {
                it.x <= triggerRadius -> {
                    onMinChanged(range.first)
                }
                it.x >= hintWidth + triggerRadius -> {
                    onMaxChanged(range.last)
                }
                (it.x <= startOffset) || it.x - startOffset < endOffset - it.x -> {
                    val new = range.first +
                            (it.x - triggerRadius) / hintWidth * (range.last - range.first)
                    onMinChanged(new)
                }
                else -> {
                    val new = range.first +
                            (it.x - triggerRadius) / hintWidth * (range.last - range.first)
                    onMaxChanged(new)
                }
            }
        }
    }.padding(padding)) {
        triggerRadius = size.height / 2
        hintWidth = size.width - triggerRadius * 2
        startOffset = triggerRadius +
                ((value.first - range.first) / (range.last - range.first)) * hintWidth
        endOffset = triggerRadius +
                ((value.last - range.first) / (range.last - range.first)) * hintWidth
        val centerX = (endOffset + startOffset) / 2
        val consumedRatio = (endOffset - startOffset) / hintWidth
        val thickness = minThickness.value +
                (1 - consumedRatio) * (triggerRadius * 2 - minThickness.value)
        drawLine(
            Color.Gray,
            center.copy(x = triggerRadius),
            center.copy(x = size.width - triggerRadius),
            hintThickness.value
        )
        val leftArcRect = Rect(Offset(startOffset, center.y), triggerRadius)
        val rightArcRect = Rect(Offset(endOffset, center.y), triggerRadius)
        path.apply {
            reset()
            moveTo(startOffset, size.height)
            arcTo(leftArcRect, 90f, 180f, false)
            cubicTo(
                startOffset + triggerRadius,
                0f,
                startOffset,
                center.y - thickness / 2,
                centerX,
                center.y - thickness / 2
            )
            cubicTo(
                endOffset,
                center.y - thickness / 2,
                endOffset - triggerRadius,
                0f,
                endOffset,
                0f
            )
            arcTo(rightArcRect, 270f, 180f, false)
            cubicTo(
                endOffset - triggerRadius,
                size.height,
                endOffset,
                center.y + thickness / 2,
                centerX,
                center.y + thickness / 2
            )
            cubicTo(
                startOffset,
                center.y + thickness / 2,
                startOffset + triggerRadius,
                size.height,
                startOffset,
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
            v = mutableStateOf(25f..75f),
            onMinChanged = { },
            onMaxChanged = { },
            range = 0f..100f,
            steps = listOf(0f, 10f, 20f, 30f, 40f, 50f, 100f),
            hintThickness = 1.dp,
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
