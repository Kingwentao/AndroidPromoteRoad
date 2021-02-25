package com.wtk.nbutil.util

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

/**
 * author: WentaoKing
 * created on: 1/25/21
 * description: 媒体工具类
 */
object MediaUtil {

    /**
     * 验证麦克风是否可使用
     */
    fun validateMicAvailability(): Boolean {
        var available: Boolean? = true
        val recorder: AudioRecord? = AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_DEFAULT, 44100)
        try {
            if (recorder!!.recordingState != AudioRecord.RECORDSTATE_STOPPED) {
                available = false
            }
            recorder.startRecording()
            if (recorder.recordingState != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop()
                available = false
            }
            recorder.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            recorder!!.release()
        }
        return available!!
    }
}