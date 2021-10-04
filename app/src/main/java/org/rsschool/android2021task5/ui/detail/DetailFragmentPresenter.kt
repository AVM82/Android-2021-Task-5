package org.rsschool.android2021task5.ui.detail

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.load.resource.gif.GifDrawable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer

class DetailFragmentPresenter(private val contentResolver: ContentResolver) {

    private var fullPath: String = ""

    private fun makeOutputStream(filename: String): OutputStream? {
        var outputStream: OutputStream?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    when (filename.substringAfterLast(".")) {
                        "gif" -> put(MediaStore.MediaColumns.MIME_TYPE, "image/gif")
                        else -> put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    }
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = imageUri?.let { resolver.openOutputStream(it) }
                fullPath = imageUri.toString()
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fullPath = image.toString()
            outputStream = FileOutputStream(image)
        }
        return outputStream
    }

    private fun saveBitmap(bitmap: Bitmap, filename: String) {
        val output = makeOutputStream(filename)
        output?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        output?.close()
    }

    private fun saveGif(buffer: ByteBuffer, filename: String) {
        val output = makeOutputStream(filename)
        val bytes = ByteArray(buffer.capacity())
        (buffer.duplicate().clear() as ByteBuffer).get(bytes)
        output?.write(bytes, 0, bytes.size)
        output?.close()
    }

    fun saveIMG(drawable: Drawable, filename: String): String {
        when (drawable) {
            is GifDrawable -> saveGif(drawable.buffer, filename)
            is BitmapDrawable -> saveBitmap(drawable.bitmap, filename)
        }
        return fullPath
    }
}
