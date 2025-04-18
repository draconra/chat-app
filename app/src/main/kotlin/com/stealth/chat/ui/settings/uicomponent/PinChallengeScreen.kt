package com.stealth.chat.ui.settings.uicomponent

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import at.favre.lib.crypto.bcrypt.BCrypt

@Composable
fun PinChallengeScreen(onPinVerified: () -> Unit) {
    var pinInput by remember { mutableStateOf("") }
    val context = LocalContext.current
    val hardPin = "123456"
    val bcryptHash = BCrypt.withDefaults().hashToString(12, hardPin.toCharArray())

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .clickable {
                keyboardController?.show()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter 6-digit PIN", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        PinBoxInput(pin = pinInput)
        BasicTextField(
            value = pinInput,
            onValueChange = {
                if (it.length <= 6 && it.all(Char::isDigit)) {
                    pinInput = it
                    if (it.length == 6) keyboardController?.hide()
                }
            },
            modifier = Modifier
                .width(1.dp)
                .height(1.dp)
                .graphicsLayer(alpha = 0f),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val result = BCrypt.verifyer().verify(pinInput.toCharArray(), bcryptHash)
                if (result.verified) {
                    onPinVerified()
                } else {
                    Toast.makeText(context, "Incorrect PIN", Toast.LENGTH_SHORT).show()
                    pinInput = ""
                    keyboardController?.show()
                }
            },
            enabled = pinInput.length == 6
        ) {
            Text("Verify")
        }
    }
}


@Composable
fun PinBoxInput(pin: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(6) { index ->
            val char = pin.getOrNull(index)?.toString() ?: ""
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (char.isNotBlank()) "●" else "",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
