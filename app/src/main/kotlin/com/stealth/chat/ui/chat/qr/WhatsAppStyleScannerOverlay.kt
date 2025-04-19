package com.stealth.chat.ui.chat.qr

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WhatsAppScannerOverlay(
    boxSize: Dp = 240.dp,
    lineColor: Color = Color.Red,
    lineThickness: Dp = 2.dp
) {
    val density = LocalDensity.current
    var scanAreaHeightPx by remember { mutableStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition()
    val scanLineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = scanAreaHeightPx,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Box(
            modifier = Modifier
                .size(boxSize)
                .align(Alignment.Center)
                .onGloballyPositioned {
                    scanAreaHeightPx = with(density) { boxSize.toPx() }
                }
                .background(Color.Transparent)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stroke = Stroke(width = 4.dp.toPx())
                drawRect(
                    color = Color.White,
                    size = size,
                    style = stroke
                )

                drawLine(
                    color = lineColor,
                    strokeWidth = lineThickness.toPx(),
                    start = Offset(0f, scanLineOffset),
                    end = Offset(size.width, scanLineOffset)
                )
            }
        }
    }
}
