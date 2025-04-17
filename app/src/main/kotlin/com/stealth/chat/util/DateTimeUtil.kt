package com.stealth.chat.util

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
object DateTimeUtils {
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())

    fun formatTime(createdAt: String): String {
        return try {
            val instant = Instant.parse(createdAt)
            timeFormatter.format(instant)
        } catch (e: Exception) {
            ""
        }
    }
}