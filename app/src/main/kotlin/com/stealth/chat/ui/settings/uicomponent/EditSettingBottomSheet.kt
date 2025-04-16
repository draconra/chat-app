package com.stealth.chat.ui.settings.uicomponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun EditSettingBottomSheet(
    title: String,
    currentValue: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newValue by remember { mutableStateOf(currentValue) }

    Dialog(onDismissRequest = onDismiss) {
        Surface {
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(12.dp)
                )
                TextField(
                    value = newValue,
                    onValueChange = { newValue = it },
                    modifier = Modifier.padding(12.dp)
                )
                Button(onClick = {
                    onSave(newValue)
                    onDismiss()
                }, modifier = Modifier.padding(12.dp)) {
                    Text("Save")
                }
            }
        }
    }
}
