package com.wtk.nbutil.util

import com.wtk.nbutil.util.ResourceFile.getFileInputStreamByName
import com.wtk.nbutil.util.ResourceFile.getFileByName
import org.junit.Assert
import org.junit.Test

/**
 * author: WentaoKing
 * created on: 1/15/21
 * description: md5工具类单元测试
 */
class MD5UtilTest {

    private val fileName = "avatar.jpg"

    @Test
    fun getFileMD5ByFile() {
        val md5String = MD5Util.getFileMD5(getFileByName(fileName))
        Assert.assertNotEquals(md5String, "")
    }

    @Test
    fun getFileMD5ByInputStream() {
        val fileInputStream = getFileInputStreamByName(fileName)
        val md5String = MD5Util.getFileMD5(fileInputStream)
        Assert.assertNotEquals(md5String, "")
    }

    /**
     * 验证多种方式获取md5值是否一致
     */
    @Test
    fun md5Equal() {
        val md5StringByFile = MD5Util.getFileMD5(getFileByName(fileName))
        val md5StringByFileInputStream = MD5Util.getFileMD5(getFileInputStreamByName(fileName))
        val isEqual = MD5Util.equalBetweenMD5(md5StringByFile, md5StringByFileInputStream!!)
        Assert.assertEquals(isEqual, true)
    }

}