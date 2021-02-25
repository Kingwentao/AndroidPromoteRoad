package com.wtk.nbutil.util

import org.junit.Test

import org.junit.Assert.*
import java.io.File

/**
 * author: WentaoKing
 * created on: 1/15/21
 * description: shell命令单元测试
 */
class ShellUtilTest {

    private val fileDir = "./src/test/java/com/cvte/dss/util/filetest/1"
    private val filePath = fileDir + File.separator + "test.txt"

    @Test
    fun executeNullCommand() {
        val result = ShellUtil.executeCommands(mutableListOf())
        assertEquals(-1, result.result)
    }

    @Test
    fun checkRootPermission() {
        val isRoot = ShellUtil.checkRootPermission()
        assertEquals(true, isRoot)
    }

    @Test
    fun executeSingleCommand() {
        //查看文件/目录
        val fileList = ShellUtil.executeCommand("ls -l", false)
        println("fileList: ${fileList.successMsg}")
        //查看当前目录
        val curDir = ShellUtil.executeCommand("pwd", false)
        println("curDir: ${curDir.successMsg}")
        //创建目录
        val createDir = ShellUtil.executeCommand("mkdir shellUtilTest", false)
        println("createDirRes: $createDir")
    }

    @Test
    fun executeManyCommand() {
        //创建文件夹
        val makeDir = "mkdir $fileDir"
        val createFile = "touch $filePath"
        val commandArrays = arrayListOf(makeDir, createFile)
        val res = ShellUtil.executeCommands(commandArrays, false)
        println("res: $res")
        assertTrue(res.result != -1)
    }

    @Test
    fun executeManyCommand2() {
        //rm -r dir 删除dir目录及其子目录下所有文件
        val rmDir = "rm -r $fileDir"
        val commandArrays = ArrayList<String>()
        commandArrays.add(rmDir)
        ShellUtil.executeCommands(commandArrays, false)
    }
}