package com.wtk.nbutil.util

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and

/**
 * author: linrunyu
 * created on: 2019-11-01
 * description: 获取MD5值
 */
object MD5Util {
    private val TAG = "MD5Util"
    private val HEX_ARRAY = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    private val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    private var messageDigest: MessageDigest? = null

    /**
     * 通过文件流获取文件md5值
     * @param inputStream 文件的输入流
     * @return md5值
     */
    fun getFileMD5(inputStream: InputStream?): String? {
        val messageDigest: MessageDigest
        val buffer = ByteArray(1024)
        try {
            messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.reset()
            var length: Int
            while (inputStream!!.read(buffer, 0, 1024).also { length = it } != -1) {
                messageDigest.update(buffer, 0, length)
            }
            return getChecksum(messageDigest)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    /**
     * 获取文件md5值
     * @param file 文件
     * @return md5值
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getFileMD5(file: File): String {
        try {
            messageDigest = MessageDigest.getInstance("MD5")
        } catch (exception: NoSuchAlgorithmException) {
            exception.printStackTrace()
        }
        val fis: InputStream = FileInputStream(file)
        val buffer = ByteArray(1024)
        var numRead: Int
        while (fis.read(buffer).also { numRead = it } > 0) {
            messageDigest!!.update(buffer, 0, numRead)
        }
        fis.close()
        return bufferToHex(messageDigest!!.digest())
    }

    private fun getChecksum(pMessageDigest: MessageDigest): String {
        val checksum = pMessageDigest.digest()
        val check = CharArray(2 * checksum.size)
        for (i in checksum.indices) {
            val sum = checksum[i]
            check[i * 2] = HEX_ARRAY[sum.toInt() ushr 4 and 15]
            check[i * 2 + 1] = HEX_ARRAY[(sum and 15).toInt()]
        }
        return String(check)
    }

    private fun bufferToHex(bytes: ByteArray, m: Int = 0, n: Int = bytes.size): String {
        val stringBuffer = StringBuffer(2 * n)
        val k = m + n
        for (l in m until k) {
            appendHexPair(bytes[l], stringBuffer)
        }
        return stringBuffer.toString()
    }

    private fun appendHexPair(bt: Byte, sb: StringBuffer) {
        val c0 = hexDigits[(bt.toInt() and 240) shr 4]
        val c1 = hexDigits[(bt.toInt() and 15)]
        sb.append(c0)
        sb.append(c1)
    }

    /**
     * 比较两个md5是否相等，不区分md5值中的大小写
     */
    fun equalBetweenMD5(a: String, b: String): Boolean {
        return a.toUpperCase(Locale.ROOT) == b.toUpperCase(Locale.ROOT)
    }

}