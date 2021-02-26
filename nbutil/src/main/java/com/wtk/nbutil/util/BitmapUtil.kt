package com.wtk.nbutil.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.*
import kotlin.math.roundToInt

/**
 * author: WentaoKing
 * created on: 1/22/21
 * description: bitmap工具类
 */
object BitmapUtil {

    /**
     * 将文件解码成bitmap
     * @param filePath String 文件路径
     * @param reqWidth Int 转换后的宽度
     * @param reqHeight Int 转换后的宽度
     * @param quality Config bitmap的质量，默认ARGB_8888
     */
    fun decodeSampledBitmap(filePath: String, reqWidth: Int, reqHeight: Int,
                            quality: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        options.inPreferredConfig = quality
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * 根据宽高计算采样率
     * @param options Options
     * @param reqWidth Int
     * @param reqHeight Int
     * @return Int
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            inSampleSize = if (heightRatio < widthRatio) widthRatio else heightRatio
        }
        return inSampleSize
    }

    /**
     * bitmap存储为图片文件
     * @param bitmap     保存的bitmap
     * @param format     压缩的bitmap文件格式
     * @param targetPath 保存路径
     * @param quality    保存的质量，默认是100
     * @throws IOException
     */
    @Throws(IOException::class)
    fun saveBitmapToImageFile(bitmap: Bitmap, format: Bitmap.CompressFormat, targetPath: String,
                              quality: Int = 100): Boolean {
        var isSuccess: Boolean
        val imageFile = File(targetPath)
        if (!imageFile.exists()) {
            val parentFile = imageFile.parentFile
            parentFile?.let {
                if (!it.exists() && !it.mkdirs()) {
                    println("saveImageFile: mkdirs fail----${parentFile.path}")
                    isSuccess = false
                }
            }
        } else {
            if (!imageFile.delete()) {
                println("saveImageFile: delete file fail----${imageFile.path}")
                isSuccess = false
            }
        }
        var outputStream: BufferedOutputStream? = null
        try {
            outputStream = BufferedOutputStream(FileOutputStream(imageFile))
            bitmap.compress(format, quality, outputStream)
            outputStream.flush()
            isSuccess = true
        } catch (e: Exception) {
            isSuccess = false
        } finally {
            outputStream?.close()
        }
        return isSuccess
    }

    /**
     * 缩放bitmap
     * @param origin Bitmap 缩放的bitmap
     * @param newWidth Float 缩放后的宽
     * @param newHeight Float 缩放后的高
     */
    fun scaleBitmap(origin: Bitmap, newWidth: Float, newHeight: Float): Bitmap? {
        val height = origin.height
        val width = origin.width
        val scaleWidth = newWidth / width.toFloat()
        val scaleHeight = newHeight / height.toFloat()
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
    }

    /**
     * 获取图片的bitmap
     * @param resources 图片资源
     * @param bitmap Int bitmap
     * @param width Int 需要的图片宽度
     * @return Bitmap
     */
    fun getImageBitmap(resources: Resources, bitmap: Int, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, bitmap, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, bitmap, options)
    }
}