package com.hyowonkim.lottonumber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Process
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 액션 바 숨기기
        this.supportActionBar?.hide()

        // 2초 동안 splash 화면 띄우기
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            this.finish()
        }, 2000)
    }

    // 뒤로 가기 이벤트 (앱 종료)
    override fun onBackPressed() {
        Process.killProcess(Process.myPid())
    }
}