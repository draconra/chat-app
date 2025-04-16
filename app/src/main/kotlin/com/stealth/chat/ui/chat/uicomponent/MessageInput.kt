package com.stealth.chat.ui.chat.uicomponent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MessageInput(
    onSend: (String, Long?) -> Unit,
    onAttachImage: () -> Unit = {}
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var animateClick by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Animate scale on click
    val scale by animateFloatAsState(
        targetValue = if (animateClick) 1.2f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "scale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ✏️ TextField with Attach Icon Inside
        TextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 56.dp, max = 150.dp),
            placeholder = { Text("Type a message...") },
            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        animateClick = true
                        onAttachImage()
                    },
                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Attach Image"
                    )
                }

                // Reset animation after delay
                LaunchedEffect(animateClick) {
                    if (animateClick) {
                        delay(150)
                        animateClick = false
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                disabledContainerColor = Color(0xFFF0F0F0)
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 🚀 Send Icon Button (Outside TextField)
        IconButton(
            onClick = {
                onSend(textState.text, 5000L)
                keyboardController?.hide()
                textState = TextFieldValue("")
            },
            enabled = textState.text.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send Message"
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MessageInputPreview() {
    MessageInput(
        onSend = { _, _ -> }, 
        onAttachImage = {}
    )
}

