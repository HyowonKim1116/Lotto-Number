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

class CheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        // 액션 바 설정
        this.supportActionBar?.setTitle("당첨정보 확인")
        this.supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.red_light))

        val webViewCheck = findViewById<WebView>(R.id.webViewCheck)
        webViewCheck.loadUrl("https://m.dhlottery.co.kr/gameResult.do?method=byWin")

        webViewCheck.webViewClient = WebViewClient()
        webViewCheck.webChromeClient = WebChromeClient()
        webViewCheck.settings.javaScriptEnabled = true
    }

    // 메뉴 아이템 설정
    override fun onCreateOptionsMenu(menu2: Menu?): Boolean {
        menuInflater.inflate(R.menu.sub_menu, menu2)
        return true
    }

    // 메뉴 아이템 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val urlCheck = findViewById<WebView>(R.id.webViewCheck).url
        when (item.itemId) {
            R.id.menuClose -> {
                this.finish()
            }
            R.id.menuCopy -> {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipdata = ClipData.newPlainText("URL", urlCheck)
                clipboard.setPrimaryClip(clipdata)
                Toast.makeText(this, "URL이 복사되었습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.menuBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlCheck))
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 뒤로 가기 이벤트
    override fun onBackPressed() {
        val webViewCheck = findViewById<WebView>(R.id.webViewCheck)
        if (webViewCheck.canGoBack()) {
            webViewCheck.goBack()
        } else {
            super.onBackPressed()
        }
    }
}