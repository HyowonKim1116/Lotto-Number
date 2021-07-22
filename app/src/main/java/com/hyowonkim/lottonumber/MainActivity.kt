package com.hyowonkim.lottonumber

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Random

class MainActivity : AppCompatActivity() {

    val random = Random() // 랜덤값 사용을 위한 기능 선언
    val numbers = arrayListOf<Int>() // 중복 제거를 위한 정수 배열 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 액션 바 배경색 설정
        this.supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.red_light))

        // 변수 선언
        val textView = findViewById<TextView>(R.id.textView)
        val runButton = findViewById<FloatingActionButton>(R.id.runButton)
        val num1 = findViewById<Button>(R.id.lottoNum1)
        val num2 = findViewById<Button>(R.id.lottoNum2)
        val num3 = findViewById<Button>(R.id.lottoNum3)
        val num4 = findViewById<Button>(R.id.lottoNum4)
        val num5 = findViewById<Button>(R.id.lottoNum5)
        val num6 = findViewById<Button>(R.id.lottoNum6)
        val numList = listOf(num1, num2, num3, num4, num5, num6)

        // runButton 클릭 이벤트
        runButton.setOnClickListener {
            // 정수 배열 numbers 초기화
            numbers.clear()
            // textView 변경 및 runButton 비활성화
            textView.text = "번호 생성 중.."
            runButton.isClickable = false
            runButton.backgroundTintList = ColorStateList.valueOf(getColor(R.color.teal_700))
            // 버튼 6개 초기화 후 0.5초 간격으로 번호 설정
            for (i in 0..5) {
                numList[i].text = ""
                numList[i].backgroundTintList = ColorStateList.valueOf(getColor(R.color.lottoNum))
                Handler().postDelayed({setLottoNum(numList[i])}, 500*(i+1).toLong())
            }
            // 번호 설정 후 textView 변경 및 runButton 재활성화
            Handler().postDelayed({
                textView.text = "오늘의 로또 번호"
                runButton.isClickable = true
                runButton.backgroundTintList = ColorStateList.valueOf(getColor(R.color.teal_200))
            }, 3000)
        }
    }

    // 로또 번호 설정 함수
    fun setLottoNum(lottoNum: Button) {
        // 랜덤값 생성
        var num = random.nextInt(45) + 1
        // 정수 배열 numbers에 num이 포함되어 있을 경우 다시 랜덤값 생성
        while (numbers.contains(num)) {
            num = random.nextInt(45) + 1
        }
        // 정수 배열 numbers에 num 추가
        numbers.add(num)
        // 번호 및 배경색 설정
        lottoNum.text = "${num}"
        if (num <= 10) { // 번호가 1~10일 경우 노란색
            lottoNum.backgroundTintList = ColorStateList.valueOf(Color.rgb(252, 196, 61))
        } else if (num <= 20) { // 번호가 11~20일 경우 파란색
            lottoNum.backgroundTintList = ColorStateList.valueOf(Color.rgb(140, 198, 231))
        } else if (num <= 30) { // 번호가 21~30일 경우 빨간색
            lottoNum.backgroundTintList = ColorStateList.valueOf(Color.rgb(241, 141, 128))
        } else if (num <= 40) { // 번호가 31~40일 경우 보라색
            lottoNum.backgroundTintList = ColorStateList.valueOf(Color.rgb(167, 162, 222))
        } else { // 번호가 41~45일 경우 초록색
            lottoNum.backgroundTintList = ColorStateList.valueOf(Color.rgb(107, 206, 158))
        }
    }

    // 메뉴 아이템 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // 메뉴 아이템 클릭 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuCheck -> {
                val intent = Intent(this, CheckActivity::class.java)
                startActivity(intent)
            }
            R.id.menuScan -> {
                val intent = Intent(this, ScanActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 앱 종료 이벤트 (뒤로 가기 두 번)
    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime >= 2000) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            Process.killProcess(Process.myPid())
        }
    }
}