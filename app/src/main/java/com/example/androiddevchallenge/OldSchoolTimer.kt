package com.example.androiddevchallenge

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.Arial
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay
import kotlin.math.hypot
import kotlin.math.min

@Composable
fun OldSchoolTimer() {
    var isPlaying by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(10) }

    LaunchedEffect(isPlaying) {
        while (countdown > 0 && isPlaying) {
            delay(1000L)
            countdown--
        }
        isPlaying = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        OldSchoolTimerScene(isPlaying, countdown) {
            OldSchoolTimerNumber(countdown)
        }
        OldSchoolTimerControls(
            isPlaying = isPlaying,
            onChangeTimerClick = { countdown = min(countdown + it, 999) },
            onPlayPauseClick = { isPlaying = !isPlaying }
        )
    }
}

@Composable
fun OldSchoolTimerScene(
    isPlaying: Boolean,
    number: Int,
    content: @Composable () -> Unit
) {
    val angle by key(number) {
        animateFloatAsState(
            targetValue = if (isPlaying) 360f else 0f,
            animationSpec = keyframes {
                durationMillis = if (isPlaying) 1000 else 0
                0f at 0 with LinearEasing
                360f at 1000 with LinearEasing
            }
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
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

@Composable
fun OldSchoolTimerControls(
    isPlaying: Boolean,
    onChangeTimerClick: (Int) -> Unit,
    onPlayPauseClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp)
    ) {
        FloatingActionButton(
            backgroundColor = Color.LightGray,
            onClick = onPlayPauseClick
        ) {
            Icon(
                painter = painterResource(
                    if (isPlaying) {
                        R.drawable.ic_pause
                    } else {
                        R.drawable.ic_play
                    }
                ),
                contentDescription = "play/pause",
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OldSchoolTimerChangeNumberControl(
                isPlaying = isPlaying,
                text = "-10",
                onClick = { onChangeTimerClick(-10) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            OldSchoolTimerChangeNumberControl(
                isPlaying = isPlaying,
                text = "-1",
                onClick = { onChangeTimerClick(-1) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            OldSchoolTimerChangeNumberControl(
                isPlaying = isPlaying,
                text = "+1",
                onClick = { onChangeTimerClick(+1) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            OldSchoolTimerChangeNumberControl(
                isPlaying = isPlaying,
                text = "+10",
                onClick = { onChangeTimerClick(+10) }
            )
        }
    }
}

@Composable
fun OldSchoolTimerChangeNumberControl(
    isPlaying: Boolean,
    text: String,
    onClick: () -> Unit
) {
    FloatingActionButton(
        backgroundColor = Color.LightGray,
        onClick = {
            if (!isPlaying) {
                onClick()
            }
        },
        modifier = Modifier.alpha(if (isPlaying) 0.5f else 1f)
    ) {
        Text(text = text)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 360,
    heightDp = 640
)
@Composable
fun OldSchoolTimerPreview() {
    MyTheme {
        OldSchoolTimer()
    }
}