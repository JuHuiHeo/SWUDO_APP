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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    // 포커스를 받을 때와 받지 않을 때의 색상을 변수로 정의
    private val textColorFocused = Color.parseColor("#00FF75")
    private val textColorNotFocused = Color.WHITE
    // firebase authentication 기능 가져오기
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // firebase authentication 기능 가져오기
        auth = Firebase.auth

        val newEmail = findViewById<EditText>(R.id.newEmail)
        val nextButton = findViewById<Button>(R.id.nextbutton)

        // "activity_main"으로 이동하는 코드
        val backButton = findViewById<Button>(R.id.backbutton)
        backButton.setOnClickListener {
            val intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // 이메일 형식이 유효한지 검사하는 함수
        fun isValidEmail(email: String): Boolean {
            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            if (pattern.matcher(email).matches()) {
                return true
            } else {
                return false
            }
        }
        // 버튼 상태를 갱신하는 함수
        fun updateButtonState(nextButton: Button, email: String) {
            // 이메일 형식이 유효한지 검사
            val isEmailValid = isValidEmail(email)
            if (isEmailValid) {
                nextButton.isEnabled = true
                nextButton.setBackgroundResource(R.drawable.nextbutton_green)
            } else {
                nextButton.isEnabled = false
                nextButton.setBackgroundResource(R.drawable.nextbutton_default)
            }
        }
        // 초기 버튼 상태 설정
        updateButtonState(nextButton, newEmail.text.toString())

        newEmail.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 클릭시 배경 변경
                    newEmail.setBackgroundResource(R.drawable.textlayout_green)
                    // 클릭시 텍스트 색상 변경
                    newEmail.setTextColor(textColorFocused)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 클릭 후 배경 원래대로
                    newEmail.setBackgroundResource(R.drawable.textlayout)
                    // 클릭 후 텍스트 색상 원래대로
                    newEmail.setTextColor(textColorNotFocused)
                }
            }
            false
        }

        newEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 포커스 받을 때 배경 변경
                newEmail.setBackgroundResource(R.drawable.textlayout_green)
                // 포커스 받을 때 텍스트 색상 변경
                newEmail.setTextColor(textColorFocused)
            } else {
                // 포커스 잃을 때 배경 원래대로
                newEmail.setBackgroundResource(R.drawable.textlayout)
                // 포커스 잃을 때 텍스트 색상 변경
                newEmail.setTextColor(textColorNotFocused)
            }
        }

        // 이메일칸의 텍스트 변화를 감지하는 TextWatcher 등록
        newEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // 이메일칸이 변경될 때마다 버튼 상태 갱신
                val emailText = s.toString().trim()
                updateButtonState(nextButton, emailText)
            }
        })

        // nextButton이 활성화 되면 "activity_signup.xml"에서 다음 페이지로 이동
        nextButton.setOnClickListener {
            if (nextButton.isEnabled) {
                val intent = Intent(this@SignupActivity, SignupPwActivity::class.java)
                startActivity(intent)
                finish() // 현재 액티비티를 종료합니다.
            }
        }

    }
}
