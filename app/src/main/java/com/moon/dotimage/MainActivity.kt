package com.moon.dotimage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.moon.dotimage.databinding.ActivityMainBinding
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(
                intent,
                FILECHOOSER_RESULT_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("MQ!", "resultCode:$resultCode, data:$data")
        if (data == null) {
            return
        }
        if (requestCode == FILECHOOSER_RESULT_CODE) {
            imageProcessing(data.data!!)
        }
    }

    private fun imageProcessing(data: Uri) {
        var bitmap: Bitmap? = null
        try {
            val inputStream = contentResolver.openInputStream(data)
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            Log.i("MQ!", "bitmap:$bitmap")
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        bitmap = rotate(bitmap!!, 90)
        binding.imageView.setImageBitmap(bitmap)
    }

    private fun rotate(bitmap: Bitmap, degrees: Int): Bitmap {
        if (degrees != 0 && bitmap != null) {
            val matrix = Matrix()
            matrix.setRotate(
                degrees.toFloat(),
                (bitmap.width / 2).toFloat(),
                (bitmap.height / 2).toFloat()
            )

            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
        return bitmap
    }

    companion object {
        const val FILECHOOSER_RESULT_CODE = 100
    }

}