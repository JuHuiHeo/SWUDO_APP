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



class MainActivity : AppCompatActivity() {
    // 포커스를 받을 때와 받지 않을 때의 색상을 변수로 정의
    private val textColorFocused = Color.parseColor("#00FF75")
    private val textColorNotFocused = Color.WHITE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // MainActivity.kt
        // editID, editPW, button, signupButton ID 불러오기
        val editID = findViewById<EditText>(R.id.editID)
        val editPW = findViewById<EditText>(R.id.editPW)
        val button = findViewById<Button>(R.id.button)
        val signupButton = findViewById<Button>(R.id.signupbutton)

        // "activity_signup.xml"로 이동하는 코드
        signupButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        // 아이디와 비밀번호가 변경될 때마다 버튼 상태 갱신
        editID.addTextChangedListener(textWatcher)
        editPW.addTextChangedListener(textWatcher)

        // 초기 버튼 상태 설정
        updateButtonState(button, editID.text.toString(), editPW.text.toString())
        button.setOnClickListener {
            // 버튼 클릭 시 아이디와 비밀번호가 모두 채워져 있을 때만 실행
            if (editID.text.isNotBlank() && editPW.text.isNotBlank()) {
                // TODO: 버튼을 눌렀을 때 실행할 동작을 여기에 작성
            }
        }

        editID.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // 클릭시 배경 변경
                editID.setBackgroundResource(R.drawable.textlayout_green)
                // 클릭시 텍스트 색상 변경
                editID.setTextColor(textColorFocused)
            } else if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                // 클릭 후 배경 원래대로
                editID.setBackgroundResource(R.drawable.textlayout)
                // 클릭 후 텍스트 색상 원래대로
                editID.setTextColor(textColorNotFocused)
            }
            false
        }

        editID.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // 포커스 받을 때 배경 변경
                editID.setBackgroundResource(R.drawable.textlayout_green)
                // 포커스 받을 때 텍스트 색상 변경
                editID.setTextColor(textColorFocused)
            } else {
                // 포커스 잃을 때 배경 원래대로
                editID.setBackgroundResource(R.drawable.textlayout)
                // 포커스 잃을 때 텍스트 색상 변경
                editID.setTextColor(textColorNotFocused)
            }
        }

        editPW.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // 클릭시 배경 변경
                editPW.setBackgroundResource(R.drawable.textlayout_green)
                // 클릭시 텍스트 색상 변경
                editPW.setTextColor(textColorFocused)
            } else if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                // 클릭 후 배경 원래대로
                editPW.setBackgroundResource(R.drawable.textlayout)
                // 클릭 후 텍스트 색상 원래대로
                editPW.setTextColor(textColorNotFocused)
            }
            false
        }

        editPW.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // 포커스 받을 때 배경 변경
                editPW.setBackgroundResource(R.drawable.textlayout_green)
                // 포커스 받을 때 텍스트 색상 변경
                editPW.setTextColor(textColorFocused)
            } else {
                // 포커스 잃을 때 배경 원래대로
                editPW.setBackgroundResource(R.drawable.textlayout)
                // 포커스 잃을 때 텍스트 색상 변경
                editPW.setTextColor(textColorNotFocused)
            }
        }
    }
    // 아이디와 비밀번호의 텍스트 변화를 감지하는 TextWatcher
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            // 아이디와 비밀번호가 변경될 때마다 버튼 상태 갱신
            val editID = findViewById<EditText>(R.id.editID)
            val editPW = findViewById<EditText>(R.id.editPW)
            val button = findViewById<Button>(R.id.button)
            updateButtonState(button, editID.text.toString(), editPW.text.toString())
        }
    }
    // 버튼 상태를 갱신하는 함수
    private fun updateButtonState(button: Button, id: String, password: String) {
        // 아이디와 비밀번호가 모두 채워져 있으면 버튼 활성화 및 배경 변경
        if (id.isNotBlank() && password.isNotBlank()) {
            button.isEnabled = true
            button.setBackgroundResource(R.drawable.loginbutton)
        } else {
            // 아이디와 비밀번호 중 하나라도 비어있으면 버튼 비활성화 및 기본 배경으로 변경
            button.isEnabled = false
            button.setBackgroundResource(R.drawable.loginbutton_default)
        }
    }


}

