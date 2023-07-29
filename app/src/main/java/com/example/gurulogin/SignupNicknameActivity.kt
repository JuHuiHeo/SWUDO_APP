package com.example.gurulogin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

class SignupNicknameActivity : AppCompatActivity() {
    // 포커스를 받을 때와 받지 않을 때의 색상을 변수로 정의
    private val textColorFocused = Color.parseColor("#00FF75")
    private val textColorNotFocused = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_nickname)

        val newNickname = findViewById<EditText>(R.id.newNickname)
        val completeButton = findViewById<Button>(R.id.completebutton)

        // "activity_main"으로 이동하는 코드
        val backButton = findViewById<Button>(R.id.backbutton3)
        backButton.setOnClickListener {
            val intent = Intent(this@SignupNicknameActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // 버튼 상태를 갱신하는 함수
        fun updateButtonState(completeButton: Button, nickname: String) {
            if (nickname.isNotBlank()) {
                completeButton.isEnabled = true
                completeButton.setBackgroundResource(R.drawable.completebutton_green)
            } else {
                completeButton.isEnabled = false
                completeButton.setBackgroundResource(R.drawable.completebutton_default)
            }
        }

        // 초기 버튼 상태 설정
        updateButtonState(completeButton, newNickname.text.toString())

        newNickname.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 클릭시 배경 변경
                    newNickname.setBackgroundResource(R.drawable.textlayout_green)
                    // 클릭시 텍스트 색상 변경
                    newNickname.setTextColor(textColorFocused)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 클릭 후 배경 원래대로
                    newNickname.setBackgroundResource(R.drawable.textlayout)
                    // 클릭 후 텍스트 색상 원래대로
                    newNickname.setTextColor(textColorNotFocused)
                }
            }
            false
        }

        newNickname.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 포커스 받을 때 배경 변경
                newNickname.setBackgroundResource(R.drawable.textlayout_green)
                // 포커스 받을 때 텍스트 색상 변경
                newNickname.setTextColor(textColorFocused)
            } else {
                // 포커스 잃을 때 배경 원래대로
                newNickname.setBackgroundResource(R.drawable.textlayout)
                // 포커스 잃을 때 텍스트 색상 변경
                newNickname.setTextColor(textColorNotFocused)
            }
        }

        // 닉네임칸의 텍스트 변화를 감지하는 TextWatcher 등록
        newNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 닉네임칸이 변경될 때마다 버튼 상태 갱신
                val nicknameText = s.toString().trim()
                updateButtonState(completeButton, nicknameText)
            }
        })

        // completeButton이 활성화 되면 "activity_signup.xml"에서 다음 페이지로 이동
        completeButton.setOnClickListener {
            if (completeButton.isEnabled) {
                val intent = Intent(this@SignupNicknameActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // 현재 액티비티를 종료합니다.
            }
        }
    }
}