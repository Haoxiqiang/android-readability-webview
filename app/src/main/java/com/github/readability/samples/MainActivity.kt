package com.github.readability.samples

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private val webView by lazy { findViewById<WebView>(R.id.webView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    private val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.INVISIBLE
        }

        @Suppress("DEPRECATION")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (processUrl(url)) {
                return true
            }
            return super.shouldOverrideUrlLoading(view, url)
        }

        fun processUrl(url: String): Boolean {
            // 拦截url点击
            if (!url.startsWith("http")) {
                return true
            }
            return url.contains("download")
        }
    }

    private val webViewChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
        }

        override fun onReceivedTitle(view: WebView, title: String?) {
            super.onReceivedTitle(view, title)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebInit.init(webView)

        webView.webChromeClient = webViewChromeClient
        webView.webViewClient = webViewClient

        webView.loadUrl(
            "file:///android_asset/readerview/readerview.html?ref=${
            URLEncoder.encode(
                "https://www.zhihu.com/question/47819047/answer/108130984",
                "UTF-8"
            )
            }"
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        SampleURLs.show(this@MainActivity, urlPicker = { url ->
            webView.loadUrl(
                "file:///android_asset/readerview/readerview.html?ref=${
                URLEncoder.encode(
                    url,
                    "UTF-8"
                )
                }"
            )
        }, dismiss = {
            menu.close()
        })
        return true
    }

    override fun onResume() {
        super.onResume()
        webView.resumeTimers()
    }

    override fun onPause() {
        super.onPause()
        webView.pauseTimers()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }
}
