package com.invictastudios.moviesapp.movies.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveImageToStorage @Inject constructor(
    private val context: Context
) {
    suspend fun saveImageToInternalStorage(fileName: String, imageUrl: String): String? {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = loader.execute(request)
        if (result is SuccessResult) {
            val bitmap = (result.drawable as BitmapDrawable).bitmap
            val directory = context.filesDir
            val file = File(directory, fileName)

            return try {
                withContext(Dispatchers.IO) {
                    FileOutputStream(file).use { outputStream ->
                        resizeBitmap(bitmap, 800, 800).compress(
                            Bitmap.CompressFormat.JPEG,
                            100,
                            outputStream
                        )
                    }
                }
                file.absolutePath
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
        return null
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = maxWidth.toFloat() / width
        val scaleHeight = maxHeight.toFloat() / height
        val scaleFactor = minOf(scaleWidth, scaleHeight)

        val matrix = Matrix()
        matrix.postScale(scaleFactor, scaleFactor)

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    fun loadImageFromStorage(filePath: String): Bitmap? {
        val file = File(filePath)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }
}