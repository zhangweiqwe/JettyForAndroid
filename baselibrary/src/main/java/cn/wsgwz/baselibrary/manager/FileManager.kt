package cn.wsgwz.baselibrary.manager

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.IOException


open class FileManager private constructor() {
    companion object {

        private var fileManager: FileManager? = null
        fun getInstance(): FileManager {
            if (fileManager == null) {
                synchronized(FileManager.javaClass) {
                    if (fileManager == null) {
                        fileManager = FileManager()
                    }
                }
            }
            return fileManager!!
        }
    }

    fun saveFile(directory: File,fileName:String, inStream: InputStream): Boolean {

        if(!directory.exists()){
            if (!directory.mkdirs()) {
                return false
            }
        }

        val out = FileOutputStream(File(directory, fileName))

        var len = 0
        val buffer = ByteArray(4096)
        len=inStream.read(buffer)
        while ((len)!=-1){
            out.write(buffer,0,len)
            len=inStream.read(buffer)
        }
        out.flush()

        inStream?.close()

        out?.close()

        return true
    }

    fun saveBitmap(context: Context, bitmap: Bitmap): String? {
        val filePic: File
        try {
            val compressFormat = Bitmap.CompressFormat.JPEG
            filePic = File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM),  "${bitmap.hashCode()}.${compressFormat.name}")
            val outputStream = FileOutputStream(filePic)
            bitmap.compress(compressFormat, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return filePic.absolutePath
    }


}