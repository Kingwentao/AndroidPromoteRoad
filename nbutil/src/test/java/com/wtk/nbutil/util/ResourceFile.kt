package com.wtk.nbutil.util

import java.io.File
import java.io.InputStream

/**
 * author: WentaoKing
 * created on: 1/16/21
 * description: resource目录下的文件获取类
 */
object ResourceFile {

    fun getFileByName(fileName: String): File {
        val classLoader = this.javaClass.classLoader
        val resourceUrl = classLoader!!.getResource(fileName)
        println("resourceUrl: ${resourceUrl.path} ")
        return File(resourceUrl.path)
    }

    fun getFilePathByName(fileName: String): String {
        val classLoader = this.javaClass.classLoader
        return classLoader!!.getResource(fileName).path
    }

    fun getFileInputStreamByName(fileName: String): InputStream {
        val classLoader = this.javaClass.classLoader
        return classLoader!!.getResourceAsStream(fileName)
    }

}