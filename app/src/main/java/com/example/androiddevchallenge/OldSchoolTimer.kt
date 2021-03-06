package com.example.androiddevchallenge

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.Arial
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay
import kotlin.math.hypot

@Composable
fun OldSchoolTimer() {
    var countdown by remember { mutableStateOf(10) }

    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000L)
            countdown--
        }
    }

    OldSchoolTimerScene(countdown) {
        OldSchoolTimerNumber(countdown)
    }
}

@Composable
fun OldSchoolTimerScene(
    number: Int,
    content: @Composable () -> Unit
) {
    val angle by key(number) {
        animateFloatAsState(
            targetValue = 360f,
            animationSpec = keyframes {
                durationMillis = 1000
                0f at 0 with LinearEasing
                360f at 1000 with LinearEasing
            }
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .drawWithCache {
                onDrawBehind {
                    drawLine(
                        color = Color.Black,
                        strokeWidth = 4f,
                        start = Offset(0f, size.center.y),
                        end = Offset(size.width, size.center.y)
                    )

                    drawLine(
                        color = Color.Black,
                        strokeWidth = 4f,
                        start = Offset(size.center.x, 0f),
                        end = Offset(size.center.x, size.height)
                    )

                    drawLine(
                        color = Color.Black,
                        strokeWidth = 8f,
                        start = Offset(size.center.x, 0f),
                        end = Offset(size.center.x, size.center.y)
                    )
                }
            }
            .drawBehind {
                val hypot = hypot(size.width, size.height)

                rotate(angle - 180f) {
                    drawLine(
                        color = Color.Black,
                        strokeWidth = 8f,
                        start = Offset(size.center.x, size.center.y),
                        end = Offset(size.center.x, hypot)
                    )
                }

                drawArc(
                    color = Color.Black,
                    startAngle = -90f,
                    sweepAngle = angle,
                    useCenter = true,
                    alpha = 0.3f,
                    topLeft = Offset(
                        x = size.center.x - hypot / 2,
                        y = size.center.y - hypot / 2
                    ),
                    size = Size(
                        width = hypot,
                        height = hypot
                    )
                )
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .border(2.dp, Color.White, CircleShape)
                .padding(16.dp)
                .border(2.dp, Color.White, CircleShape)
        ) {
            content()
        }
    }
}

@Composable
fun OldSchoolTimerNumber(number: Int) {
    Text(
        color = Color.Black,
        fontSize = 200.sp,
        fontFamily = Arial,
        fontWeight = FontWeight.W900,
        text = number.toString()
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 640,
    heightDp = 360
)
@Composable
fun OldSchoolTimerPreview() {
    MyTheme {
        OldSchoolTimer()
    }
}