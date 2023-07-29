package com.example.gurulogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText

class SignupPwActivity : AppCompatActivity() {
    // 포커스를 받을 때와 받지 않을 때의 색상을 변수로 정의
    private val textColorFocused = Color.parseColor("#00FF75")
    private val textColorNotFocused = Color.WHITE

    // Id 불러오기
    private lateinit var newPw: EditText
    private lateinit var newPwCheck: EditText
    private lateinit var nextButton2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_pw)

        // "activity_main"으로 이동하는 코드
        val backButton2 = findViewById<Button>(R.id.backbutton2)
        backButton2.setOnClickListener {
            val intent = Intent(this@SignupPwActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // EditText와 Button 초기화
        newPw = findViewById(R.id.newPw)
        newPwCheck = findViewById(R.id.newPwCheck)
        nextButton2 = findViewById(R.id.nextbutton2)

        // 초기 버튼 상태 설정
        updateButtonState(nextButton2, newPw.text.toString(), newPwCheck.text.toString())

        newPw.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 클릭시 배경 변경
                    newPw.setBackgroundResource(R.drawable.textlayout_green)
                    // 클릭시 텍스트 색상 변경
                    newPw.setTextColor(textColorFocused)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 클릭 후 배경 원래대로
                    newPw.setBackgroundResource(R.drawable.textlayout)
                    // 클릭 후 텍스트 색상 원래대로
                    newPw.setTextColor(textColorNotFocused)
                }
            }
            false
        }

        newPw.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 포커스 받을 때 배경 변경
                newPw.setBackgroundResource(R.drawable.textlayout_green)
                // 포커스 받을 때 텍스트 색상 변경
                newPw.setTextColor(textColorFocused)
            } else {
                // 포커스 잃을 때 배경 원래대로
                newPw.setBackgroundResource(R.drawable.textlayout)
                // 포커스 잃을 때 텍스트 색상 변경
                newPw.setTextColor(textColorNotFocused)
            }
        }

        newPwCheck.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 클릭시 배경 변경
                    newPwCheck.setBackgroundResource(R.drawable.textlayout_green)
                    // 클릭시 텍스트 색상 변경
                    newPwCheck.setTextColor(textColorFocused)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 클릭 후 배경 원래대로
                    newPwCheck.setBackgroundResource(R.drawable.textlayout)
                    // 클릭 후 텍스트 색상 원래대로
                    newPwCheck.setTextColor(textColorNotFocused)
                }
            }
            false
        }

        newPwCheck.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 포커스 받을 때 배경 변경
                newPwCheck.setBackgroundResource(R.drawable.textlayout_green)
                // 포커스 받을 때 텍스트 색상 변경
                newPwCheck.setTextColor(textColorFocused)
            } else {
                // 포커스 잃을 때 배경 원래대로
                newPwCheck.setBackgroundResource(R.drawable.textlayout)
                // 포커스 잃을 때 텍스트 색상 변경
                newPwCheck.setTextColor(textColorNotFocused)
            }
        }

        // 비밀번호가 동일한지 검사하는 함수 호출
        newPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 비밀번호칸이 변경될 때마다 버튼 상태 갱신
                val pwText = s.toString().trim()
                val pwCheckText = newPwCheck.text.toString().trim()
                updateButtonState(nextButton2, pwText, pwCheckText)
            }
        })

        newPwCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 비밀번호칸이 변경될 때마다 버튼 상태 갱신
                val pwText = newPw.text.toString().trim()
                val pwCheckText = s.toString().trim()
                updateButtonState(nextButton2, pwText, pwCheckText)
            }
        })

        // 다음 페이지로 이동하는 코드
        nextButton2.setOnClickListener {
            if (nextButton2.isEnabled) {
                val intent = Intent(this@SignupPwActivity, SignupNicknameActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // 비밀번호가 동일한지 검사하는 함수
    private fun isSamePw(pw: String, pwCheck: String): Boolean {
        if (pw.isNotBlank() && pwCheck.isNotBlank() && pw == pwCheck) {
            return true
        } else {
            return false
        }
    }

    // 버튼 상태를 갱신하는 함수
    private fun updateButtonState(nextButton: Button, pw: String, pwCheck: String) {
        // 비밀번호가 동일한지 검사
        val isSamePw = isSamePw(pw, pwCheck)
        if (isSamePw) {
            nextButton.isEnabled = true
            nextButton.setBackgroundResource(R.drawable.nextbutton_green)
        } else {
            nextButton.isEnabled = false
            nextButton.setBackgroundResource(R.drawable.nextbutton_default)
        }
    }
}
