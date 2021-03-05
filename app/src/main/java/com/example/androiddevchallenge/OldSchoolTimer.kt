package com.example.androiddevchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.Arial
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun OldSchoolTimer() {
    OldSchoolTimerScene {
        OldSchoolTimerNumber(1)
    }
}

@Composable
fun OldSchoolTimerScene(
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .drawBehind {
                drawLine(
                    color = Color.Black,
                    strokeWidth = 4f,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2)
                )

                drawLine(
                    color = Color.Black,
                    strokeWidth = 4f,
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height)
                )
            },
        content = { content() }
    )
}

@Composable
fun OldSchoolTimerNumber(number: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(16.dp)
            .border(2.dp, Color.White, CircleShape)
            .padding(32.dp)
            .border(2.dp, Color.White, CircleShape)
    ) {
        Text(
            color = Color.Black,
            fontSize = 200.sp,
            fontFamily = Arial,
            fontWeight = FontWeight.W900,
            text = number.toString()
        )
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