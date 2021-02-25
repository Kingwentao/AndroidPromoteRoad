package com.wtk.nbutil.util

import java.io.*

/**
 * author: WentaoKing
 * created on: 1/21/21
 * description: 文件操作工具类
 */
object FileUtil {

    const val MODE_COVER = 1
    const val MODE_UNCOVER = 0

    /**
     * 创建文件夹，可同时创建多层文件夹
     * @param path String 创建的文件夹路径
     * @param mode Int 创建模式：覆盖创建/不覆盖
     */
    fun createFolder(path: String, mode: Int = MODE_COVER): Boolean {
        try {
            val file = File(path)
            if (file.exists()) {
                if (mode == MODE_COVER) {
                    file.delete()
                    return file.mkdirs()
                }
            } else {
                return file.mkdirs()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 创建文件
     * @param path String 文件路径
     * @param mode Int 创建模式：覆盖创建/不覆盖
     */
    fun createFile(path: String, mode: Int = MODE_COVER): Boolean {
        if (path.isEmpty()) {
            return false
        }
        try {
            val file = File(path)
            if (file.exists()) {
                if (mode == MODE_COVER) {
                    file.delete()
                    file.createNewFile()
                }
            } else {
                val mFile = file.parentFile
                mFile?.let {
                    if (!it.exists()) {
                        it.mkdirs()
                    }
                }
                file.createNewFile()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 1.文件路径若是文件夹：删除目录下所有文件/文件夹，可删除父文件夹
     * 2.文件路径若是文件：删除该文件，不删除父目录
     * @param filePath String
     * @param deleteParent Boolean 文件夹路径下是否删除该目录
     */
    fun deleteFile(filePath: String, deleteParent: Boolean = false): Boolean {
        if (filePath.isEmpty()) {
            return false
        }
        try {
            val deleteFile = File(filePath)
            if (deleteFile.exists() && deleteFile.isDirectory) {
                val delFiles = deleteFile.listFiles()
                if (delFiles != null) {
                    for (i in delFiles.indices) {
                        deleteFile(delFiles[i].absolutePath, deleteParent)
                    }
                }
            }
            if (deleteParent) {
                deleteFile.delete()
            } else if (deleteFile.isFile) {
                deleteFile.delete()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    /**
     * 获取文件类型
     */
    fun getFileType(filePath: String): String {
        if (filePath.isNotEmpty()) {
            val typeIndex = filePath.lastIndexOf(".")
            if (typeIndex != -1) {
                return filePath.substring(typeIndex + 1).toLowerCase()
            }
        }
        return ""
    }

    /**
     * 复制文件
     * @param fromFilePath String 复制前的文件路径
     * @param toFilePath String 复制后的文件路径
     * @param rewrite Boolean 是否重写
     */
    fun copyFile(fromFilePath: String, toFilePath: String, rewrite: Boolean): Boolean {
        val fromFile = File(fromFilePath)
        val toFile = File(toFilePath)
        return copyFile(fromFile, toFile, rewrite)
    }

    /**
     * 复制文件
     * @param fromFile File 复制前的文件
     * @param toFile File 复制后的文件
     * @param rewrite Boolean 是否重写
     */
    fun copyFile(fromFile: File, toFile: File, rewrite: Boolean): Boolean {
        if (fromFile.exists() && fromFile.isFile && fromFile.canRead()) {
            val parentFile = toFile.parentFile
            parentFile?.let {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            if (toFile.exists() && rewrite) {
                toFile.delete()
            }
            var fin: FileInputStream? = null
            var fos: FileOutputStream? = null
            try {
                fin = FileInputStream(fromFile)
                fos = FileOutputStream(toFile)
                val buffer = ByteArray(1024)
                var c: Int
                while (fin.read(buffer).also { c = it } > 0) {
                    fos.write(buffer, 0, c)
                }
                return true
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                fin?.close()
                fos?.close()
            }
        }
        return false
    }

    /**
     * 文件重命名
     * @param path String 文件路径
     * @param newName String 新文件名
     */
    fun rename(path: String, newName: String): Boolean {
        var result = false
        val file = File(path)
        if (path.isNotEmpty() && newName.isNotEmpty() && file.exists()) {
            try {
                file.parent?.let {
                    val newPath = it + File.separator + newName
                    result = file.renameTo(File(newPath))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    /**
     * 获取不包含扩展名的文件名
     * @param name String 文件名
     */
    fun getFileNameExceptExtension(name: String): String {
        return if (name.isEmpty()) {
            ""
        } else {
            val pos = name.lastIndexOf(46.toChar())
            if (pos != -1 && pos != 0) name.substring(0, pos) else ""
        }
    }

    /**
     * 通过文件名获取扩展名
     */
    fun getFileExtension(fileName: String): String {
        if (fileName.isNotEmpty()) {
            val dotPos = fileName.lastIndexOf('.')
            if (0 <= dotPos) {
                return fileName.substring(dotPos + 1).toLowerCase()
            }
        }
        return ""
    }

    /**
     * 根据路径截取文件名
     */
    fun getFileNameByPath(filePath: String): String {
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1)
    }

    /**
     * 写入流到文件中
     */
    fun writeStreamToFile(inputStream: InputStream, file: File): Boolean {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            val byte = ByteArray(1024)
            var len: Int
            do {
                len = inputStream.read(byte)
                if (len != -1) fos.write(byte, 0, len)
                else break
            } while (true)
            return true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            fos?.close()
        }
        return false
    }

}