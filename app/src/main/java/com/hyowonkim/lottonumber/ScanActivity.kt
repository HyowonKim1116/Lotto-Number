package com.hyowonkim.lottonumber

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        // 액션 바 설정
        this.supportActionBar?.setTitle("QR코드 스캔 결과")
        this.supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.red_light))

        val integrator = IntentIntegrator(this)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 인증해주세요.")
        integrator.initiateScan()
    }

    // QR코드 스캐너 이벤트
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val webViewScan = findViewById<WebView>(R.id.webViewScan)

        webViewScan.webViewClient = WebViewClient()
        webViewScan.webChromeClient = WebChromeClient()
        webViewScan.settings.javaScriptEnabled = true

        if (result.contents == null) {this.finish()}
        else {webViewScan.loadUrl(result.contents)}
        super.onActivityResult(requestCode, resultCode, data)
    }

    // 메뉴 아이템 설정
    override fun onCreateOptionsMenu(menu2: Menu?): Boolean {
        menuInflater.inflate(R.menu.sub_menu, menu2)
        return true
    }

    // 메뉴 아이템 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val urlScan = findViewById<WebView>(R.id.webViewScan).url
        when (item.itemId) {
            R.id.menuClose -> {
                this.finish()
            }
            R.id.menuCopy -> {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipdata = ClipData.newPlainText("URL", urlScan)
                clipboard.setPrimaryClip(clipdata)
                Toast.makeText(this, "URL이 복사되었습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.menuBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlScan))
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 뒤로 가기 이벤트
    override fun onBackPressed() {
        val webViewScan = findViewById<WebView>(R.id.webViewScan)
        if (webViewScan.canGoBack()) {
            webViewScan.goBack()
        } else {
            super.onBackPressed()
        }
    }
}