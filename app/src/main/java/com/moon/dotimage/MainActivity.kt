package com.moon.dotimage

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.moon.dotimage.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var bitmap: Bitmap? = null

    private var threshold = 127

    private var width = 50

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

        binding.webview.run {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            addJavascriptInterface(WebAppInterface(this@MainActivity), "Dot")
            loadUrl("file:///android_asset/dot.html")
            webViewClient = object: WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.i("MQ!", "onPageFinished")
                }
            }
        }
    }

    inner class WebAppInterface(private val context: Context) {
        @JavascriptInterface
        fun bitmapToBase64(): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            Log.i("MQ!", "bitmapToBase64:$byteArray")
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        @JavascriptInterface
        fun getThreshold(): Int {
            return threshold
        }

        @JavascriptInterface
        fun getWidth(): Int {
            return width
        }

        @JavascriptInterface
        fun getDoubleNum(num: Int) {
            Log.i("MQ!", "getDoubleNum : $num")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("MQ!", "resultCode:$requestCode, data:$data")
        if (data == null) {
            return
        }
        if (requestCode == FILECHOOSER_RESULT_CODE) {
            imageProcessing(data.data!!)
        }
    }

    private fun imageProcessing(data: Uri) {
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
        binding.webview.loadUrl("javascript:getBitmap()")
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