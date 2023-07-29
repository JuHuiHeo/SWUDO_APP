package com.example.gurulogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class SignupPwActivity : AppCompatActivity() {
    // 포커스를 받을 때와 받지 않을 때의 색상을 변수로 정의
    private val textColorFocused = Color.parseColor("#00FF75")
    private val textColorNotFocused = Color.WHITE

    // Id 불러오기
    private lateinit var newPw: EditText
    private lateinit var newPwCheck: EditText
    private lateinit var nextButton2: Button

    // firebase 설정
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_pw)

        // Firebase 인증 객체 초기화
        auth = FirebaseAuth.getInstance()
        // Firebase Firestore 객체 초기화
        firestore = FirebaseFirestore.getInstance()

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

        // 이전 액티비티에서 전달받은 이메일 정보 가져오기
        val email = intent.getStringExtra("email")

        // 다음 버튼 클릭 이벤트 처리
        nextButton2.setOnClickListener {
            // 비밀번호 입력란의 텍스트 가져오기 (공백 제거)
            val password = newPw.text.toString().trim()
            val passwordCheck = newPwCheck.text.toString().trim()

            // 비밀번호가 동일한지 검사
            if (isSamePw(password, passwordCheck)) {
                // 비밀번호가 동일하면 닉네임 입력 화면으로 이동
                val intent = Intent(this@SignupPwActivity, SignupNicknameActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                // 비밀번호가 다르면 사용자에게 알림
                // 예를 들어, Toast 메시지를 띄우는 등의 처리를 추가하면 됨
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
