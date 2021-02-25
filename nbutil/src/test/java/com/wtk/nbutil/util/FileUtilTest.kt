package com.wtk.nbutil.util

import org.junit.Assert
import org.junit.Test
import java.io.File

/**
 * author: WentaoKing
 * created on: 1/21/21
 * description: 文件工具类测试
 */
class FileUtilTest {

    private val rootDir = "./src/test/java/com/cvte/dss/util/filetest"
    private val fileDir = "./src/test/java/com/cvte/dss/util/filetest/1"
    private val filePath = fileDir + File.separator + "test.txt"
    private val copyDir = "./src/test/java/com/cvte/dss/util/filetest/2"
    private val copyFilePath = copyDir + File.separator + "test.txt"
    private val copyDir2 = "./src/test/java/com/cvte/dss/util/filetest/2/copy"
    private val copyFilePath2 = copyDir2 + File.separator + "test2.txt"
    private val emptyPath = ""
    private val newName = "test2.txt"

    fun prepare() {
        createFolder()
        createFile()
        copyFile()
        copyFileByPath()
    }

    @Test
    fun createFolder() {
        val res1 = FileUtil.createFolder(fileDir, FileUtil.MODE_COVER)
        Assert.assertTrue(res1)
        val res2 = FileUtil.createFolder(emptyPath, FileUtil.MODE_COVER)
        Assert.assertFalse(res2)
    }

    @Test
    fun createFile() {
        val res = FileUtil.createFile(filePath, FileUtil.MODE_COVER)
        Assert.assertTrue(res)
        //测试空路径
        val eRes = FileUtil.createFile(emptyPath)
        Assert.assertFalse(eRes)
    }

    @Test
    fun copyFile() {
        val fromFile = File(filePath)
        val toFile = File(copyFilePath)
        val copyRes = FileUtil.copyFile(fromFile, toFile, true)
        if (fromFile.exists() && toFile.exists()) {
            Assert.assertTrue(copyRes)
        } else {
            Assert.assertFalse(copyRes)
        }
    }

    @Test
    fun copyFileByPath() {
        val copyRes2 = FileUtil.copyFile(filePath, copyFilePath2, true)
        if (File(filePath).exists() && File(copyFilePath2).exists()) {
            Assert.assertTrue(copyRes2)
        } else {
            Assert.assertFalse(copyRes2)
        }
    }

    @Test
    fun deleteFile() {
        //删除文件
        val res1 = FileUtil.deleteFile(fileDir)
        Assert.assertTrue(res1)
        //测试空路径
        val eRes = FileUtil.deleteFile(emptyPath)
        Assert.assertFalse(eRes)

        val res2 = FileUtil.deleteFile(copyDir2, false)
        Assert.assertTrue(res2)
    }

    @Test
    fun deleteFileAndParentDir() {
        //删除文件
        val res = FileUtil.deleteFile(fileDir, true)
        Assert.assertTrue(res)
        val res1 = FileUtil.deleteFile(copyDir, true)
        Assert.assertTrue(res1)
    }

    @Test
    fun deleteDirOfAllFile() {
        //删除目录下所有文件并删除该目录
        val res = FileUtil.deleteFile(rootDir, true)
        Assert.assertTrue(res)
    }

    @Test
    fun getFileType() {
        val type = FileUtil.getFileType(filePath)
        Assert.assertTrue(type == "txt")
        val empty = FileUtil.getFileType(emptyPath)
        Assert.assertTrue(empty == "")
    }

    @Test
    fun renameFile() {
        val res = FileUtil.rename(copyFilePath2, newName)
        if (File(copyFilePath2).exists()) {
            Assert.assertTrue(res)
        } else {
            Assert.assertFalse(res)
        }
    }

    @Test
    fun testGetFileName() {
        val name = FileUtil.getFileNameExceptExtension(newName)
        println("name: $name")
        Assert.assertNotNull(name)

        val name2 = FileUtil.getFileNameByPath(copyFilePath2)
        println("name2: $name2")
        Assert.assertEquals("test2.txt", name2)

        val fileExtension = FileUtil.getFileExtension(newName)
        println("extension: $fileExtension")
        Assert.assertNotNull(fileExtension)
    }

    @Test
    fun testInputStreamToFile() {
        val file = File(copyFilePath)
        if (File(copyFilePath).exists() && File(copyFilePath2).exists()) {
            val res = FileUtil.writeStreamToFile(file.inputStream(), File(copyFilePath2))
            Assert.assertTrue(res)
        }
    }

    @Test
    fun destroy() {
        deleteDirOfAllFile()
    }

}