package com.example.gurulogin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignupNicknameActivity : AppCompatActivity() {
    // 포커스를 받을 때와 받지 않을 때의 색상을 변수로 정의
    private val textColorFocused = Color.parseColor("#00FF75")
    private val textColorNotFocused = Color.WHITE

    // firebase 연동
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_nickname)

        // Firebase 인증 객체 초기화
        auth = FirebaseAuth.getInstance()
        // Firebase Firestore 객체 초기화
        firestore = FirebaseFirestore.getInstance()

        // 각 id 받아오기
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

        // 이전 액티비티에서 전달받은 이메일과 비밀번호 정보 가져오기
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        // completeButton이 활성화 되면 "activity_signup.xml"에서 다음 페이지로 이동
        completeButton.setOnClickListener {
            // 닉네임 입력란의 텍스트 가져오기 (공백 제거)
            val nickname = newNickname.text.toString().trim()

            // Firebase 회원가입 처리
            auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        // 사용자 정보 Firestore에 저장
                        if (user != null) {
                            val userData = hashMapOf(
                                "email" to email,
                                "nickname" to nickname
                                // 다른 필요한 정보도 추가 가능
                            )
                            firestore.collection("users").document(user.uid)
                                .set(userData)
                                .addOnSuccessListener {
                                    // 회원가입 완료 후 로그인 화면으로 이동
                                    val intent = Intent(
                                        this@SignupNicknameActivity,MainActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    // 저장 실패 처리
                                    Log.e("SignupNicknameActivity", "Firestore 저장 실패: ${e.message}")
                                }
                        }
                    } else {
                        // 회원가입 실패 처리
                        Log.e("SignupNicknameActivity", "회원가입 실패: ${task.exception?.message}")
                    }
                }
        }
    }
}