package com.stealth.chat.ui.chat.uicomponent

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.stealth.chat.model.Message
import com.stealth.chat.util.DateTimeUtils
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun ChatBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (message.isSentByMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isSentByMe) Color(0xFFDCF8C6) else Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(
                        topStart = if (message.isSentByMe) 12.dp else 0.dp,
                        topEnd = if (message.isSentByMe) 0.dp else 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                message.imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Image Message",
                        modifier = Modifier
                            .widthIn(max = 240.dp) // ✅ limit width
                            .heightIn(min = 100.dp, max = 200.dp) // ✅ reasonable image height range
                            .clip(RoundedCornerShape(10.dp)) // ✅ rounded corner
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                if (message.text.isNotBlank()) {
                    Text(
                        text = message.text,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = DateTimeUtils.formatTime(message.createdAt),
                    fontSize = 10.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
//                if (message.disappearAfterMillis != null) {
//                    Text(
//                        text = "Disappears in ${(message.disappearAfterMillis / 1000)}s",
//                        fontSize = 10.sp,
//                        color = Color.DarkGray,
//                        modifier = Modifier.padding(top = 4.dp)
//                    )
//                }
            }
        }
    }
}
