package com.moon.dotimage

import android.content.ClipboardManager
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

        binding.webview.loadUrl("https://lachlanarthur.github.io/Braille-ASCII-Art/")

        binding.paste.setOnClickListener {
            var clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            Log.i("MQ!", "clipboard: ${clipboardManager.text}")
        }
        binding.button.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
//                    Uri.parse("https://lachlanarthur.github.io/Braille-ASCII-Art/")
                    Uri.parse("https://505e06b2.github.io/Image-to-Braille/")
                )
            )
        }
    }
}