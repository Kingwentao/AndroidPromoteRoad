package com.wtk.nbutil.util

import android.net.LocalSocket
import android.net.LocalSocketAddress
import android.util.Log
import java.io.*

object ShellUtil {

    private const val TAG = "ShellUtil"
    private const val COMMAND_SU = "su"
    private const val COMMAND_SH = "sh"
    private const val COMMAND_EXIT = "exit\n"
    private const val COMMAND_LINE_END = "\n"

    /**
     * check whether has root permission
     * always return true because the one who uses ShellCommandUtil
     * definitely can execute command with root permission
     */
    fun checkRootPermission(): Boolean {
        return true
    }

    /**
     * execute shell command
     * @param command         command
     * @param isRoot          whether need to run with root
     * @param isNeedResultMsg whether need result msg
     */
    fun executeCommand(command: String, isRoot: Boolean = true, isNeedResultMsg: Boolean = true): CommandResult {
        return executeCommands(arrayOf(command), isRoot, isNeedResultMsg)
    }

    /**
     * execute shell commands
     * @param commands        command list
     * @param isRoot          whether need to run with root
     * @param isNeedResultMsg whether need result msg
     */
    fun executeCommands(commands: List<String>, isRoot: Boolean = true, isNeedResultMsg: Boolean = true): CommandResult {
        return executeCommands(commands.toTypedArray(), isRoot, isNeedResultMsg)
    }

    /**
     * execute shell commands
     * @param commands        command array
     * @param isRoot          whether need to run with root
     * @param isNeedResultMsg whether need result msg
     */
    fun executeCommands(commands: Array<String>, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
        if (commands.isEmpty()) {
            return CommandResult(-1, null, null)
        }
        return if (isRoot) {
            executeCommandWithRootPermission(commands, isNeedResultMsg)
        } else {
            executeCommandWithUserPermission(commands, isNeedResultMsg)
        }
    }

    private fun executeCommandWithUserPermission(commands: Array<String>, isNeedResultMsg: Boolean): CommandResult {
        var result = -1
        var process: Process? = null
        var successResult: BufferedReader? = null
        var errorResult: BufferedReader? = null
        var successMsg: StringBuilder? = null
        var errorMsg: StringBuilder? = null
        var os: DataOutputStream? = null
        try {
            process = Runtime.getRuntime().exec(COMMAND_SH)
            os = DataOutputStream(process.outputStream)
            for (command in commands) {
                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.toByteArray())
                os.writeBytes(COMMAND_LINE_END)
                os.flush()
            }
            os.writeBytes(COMMAND_EXIT)
            os.flush()
            result = process.waitFor()
            // get command result
            if (isNeedResultMsg) {
                successMsg = StringBuilder()
                errorMsg = StringBuilder()
                successResult = BufferedReader(InputStreamReader(process.inputStream))
                errorResult = BufferedReader(InputStreamReader(process.errorStream))
                var s: String?
                while (successResult.readLine().also { s = it } != null) {
                    successMsg.append(s)
                    successMsg.append('\n')
                }
                while (errorResult.readLine().also { s = it } != null) {
                    errorMsg.append(s)
                    successMsg.append('\n')
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "execute shell command error", e)
        } catch (e: InterruptedException) {
            Log.e(TAG, "process.waitFor exception", e)
        } finally {
            try {
                os?.close()
                successResult?.close()
                errorResult?.close()
            } catch (e: IOException) {
                Log.e(TAG, "close stream error", e)
            }
            process?.destroy()
        }
        return CommandResult(result, successMsg?.toString(), errorMsg?.toString())
    }

    /**
     * result of command
     */
    data class CommandResult(
            /**
             * result of command
             */
            var result: Int,
            /**
             * success message of command result
             */
            var successMsg: String?,
            /**
             * error message of command result
             */
            var errorMsg: String?
    )

    private fun executeCommandWithRootPermission(commands: Array<String>, isNeedResultMsg: Boolean): CommandResult {
        val cmdBuilder = StringBuilder()
        for (i in commands.indices) {
            cmdBuilder.append(commands[i])
            if (i != commands.size - 1) {
                cmdBuilder.append(" && ")
            }
        }
        val command = cmdBuilder.toString()
        var mSocket: LocalSocket? = null
        var mIn: InputStream? = null
        var mOut: OutputStream? = null
        var line: String? = ""
        val sb = StringBuilder(line!!)
        try {
            //创建socket
            mSocket = LocalSocket()
            //设置连接地址
            val address = LocalSocketAddress("rootdaemon", LocalSocketAddress.Namespace.RESERVED)
            //建立连接
            mSocket.connect(address)
            //获取数据输出流 可以写数据
            mOut = mSocket.outputStream
            mOut.write(command.toByteArray())
            //获取数据输入流 可以读数据
            mIn = mSocket.inputStream
            val inputStream = mIn
            val insReader = InputStreamReader(inputStream)
            val bufReader = BufferedReader(insReader)
            while (bufReader.readLine().also { line = it } != null) {
                sb.append(line)
                sb.append('\n')
            }
        } catch (e: IOException) {
            Log.e(TAG, "execute root shell command error", e)
        } finally {
            try {
                mIn?.close()
                mOut?.close()
                mSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "close stream error", e)
            }
        }
        return getCommandResultFromBuilder(sb, isNeedResultMsg)
    }

    private fun getCommandResultFromBuilder(buffer: StringBuilder, isNeedResultMsg: Boolean): CommandResult {
        var successMsg: String? = null
        var errorMsg: String? = null
        val result = getResultSignFromBuilder(buffer)
        if (result == 0) {
            successMsg = if (isNeedResultMsg) getMessageFromBuilder(buffer) else null
        } else {
            errorMsg = if (isNeedResultMsg) getMessageFromBuilder(buffer) else null
        }
        return CommandResult(result, successMsg, errorMsg)
    }

    private fun getResultSignFromBuilder(buffer: StringBuilder): Int {
        val readStrings = buffer.toString().split(COMMAND_LINE_END.toRegex()).toTypedArray()
        if (readStrings.isNotEmpty()) {
            val resultStrings = readStrings[readStrings.size - 1].split(" ".toRegex()).toTypedArray()
            if (resultStrings.size == 3) {
                return resultStrings[1].toInt()
            }
        }
        return -1
    }

    private fun getMessageFromBuilder(buffer: StringBuilder): String? {
        val readStrings = buffer.toString().split(COMMAND_LINE_END.toRegex()).toTypedArray()
        val msgBuffer = StringBuilder("")
        for (i in 0 until readStrings.size - 1) {
            msgBuffer.append(readStrings[i])
            msgBuffer.append('\n')
        }
        return msgBuffer.toString()
    }

}