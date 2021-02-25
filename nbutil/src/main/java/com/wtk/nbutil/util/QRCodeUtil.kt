package com.wtk.nbutil.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

/**
 * author: WentaoKing
 * created on: 1/20/21
 * description: 二维码工具类
 */
object QRCodeUtil {

    fun generateQRCode(text: String, width: Int, height: Int, margin: Int = 0): Bitmap? {
        return try {
            val hints = Hashtable<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.MARGIN] = margin
            val writer = QRCodeWriter()
            val matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints)
            val w = matrix.width
            val h = matrix.height
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
            for (i in 0 until w) {
                for (j in 0 until h) {
                    bitmap.setPixel(i, j, if (matrix.get(i, j)) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

}