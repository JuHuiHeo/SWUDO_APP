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
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ExActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var nicknameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex)

        // Firebase 인증과 Firestore 초기화
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // nicknameTextView 참조
        nicknameTextView = findViewById<TextView>(R.id.nicknameTextView)

        // Firebase에서 사용자 정보 가져오기
        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val nickname = documentSnapshot.getString("nickname")
                        showNickname(nickname)
                    } else {
                        showNickname("닉네임 없음")
                    }
                }
                .addOnFailureListener {
                    showNickname("닉네임 가져오기 실패")
                }
        }
    }

    // 닉네임을 화면에 표시하는 함수
    private fun showNickname(nickname: String?) {
        nicknameTextView.text = nickname ?: "닉네임 없음"
    }
}

