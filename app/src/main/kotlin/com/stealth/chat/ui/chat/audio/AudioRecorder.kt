package com.stealth.chat.ui.chat.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import com.stealth.chat.model.MessageAudio
import java.io.File

class AudioRecorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    fun startRecording() {
        val outputDir = context.cacheDir
        audioFile = File.createTempFile("audio_", ".mp3", outputDir)

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile?.absolutePath)
            prepare()
            start()
        }
    }

    fun stopRecording(): MessageAudio? {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        return audioFile?.let {
            val duration = getAudioDuration(Uri.fromFile(it))
            MessageAudio(uri = it.absolutePath, duration = duration)
        }
    }

    private fun getAudioDuration(uri: Uri): Long {
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(context, uri)
            prepare()
        }
        val duration = mediaPlayer.duration.toLong()
        mediaPlayer.release()
        return duration
    }
}
