package com.stealth.chat.util

import com.stealth.chat.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ChatUtils {

    /**
     * Start countdown to remove a message after [duration] milliseconds.
     *
     * @param scope CoroutineScope to launch the countdown
     * @param messageId ID of the message to remove
     * @param currentMessages Current list of messages
     * @param duration Delay before removal (ms)
     * @param onMessagesUpdated Callback to deliver updated message list
     */
    fun startDisappearCountdown(
        scope: CoroutineScope,
        messageId: Int,
        currentMessages: List<Message>,
        duration: Long,
        onMessagesUpdated: (List<Message>) -> Unit
    ) {
        scope.launch {
            delay(duration)
            val updated = currentMessages.filterNot { it.id == messageId }
            onMessagesUpdated(updated)
        }
    }
}
