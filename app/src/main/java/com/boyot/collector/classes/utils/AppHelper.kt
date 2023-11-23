package com.boyot.collector.classes.utils

import android.content.Context
import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Random


object AppHelper {


    fun getMultipartBodyImage(context: Context,bitmap: Bitmap, name: String?): MultipartBody.Part {
        val filesDir = context.filesDir
        val f = File(filesDir, "imageName" + random() + ".jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
        }
        val reqFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), f)
        return MultipartBody.Part.createFormData(name!!, f.name, reqFile)
    }
    fun random(): String {
        val generator = Random()
        val randomStringBuilder = StringBuilder()
        val randomLength = generator.nextInt(9)
        var tempChar: Char
        for (i in 0 until randomLength) {
            tempChar = (generator.nextInt(96) + 32).toChar()
            randomStringBuilder.append(tempChar)
        }
        return randomStringBuilder.toString()
    }
    fun toRequestBody(value: String?): RequestBody {
        return value!!.toRequestBody("text/plain".toMediaTypeOrNull())
    }

}