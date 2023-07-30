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
import androidx.core.text.HtmlCompat
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var nicknameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

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
                        val welcomeMessage = getString(R.string.welcome_message, nickname)
                        showNickname(welcomeMessage)
                    } else {
                        val welcomeMessage = getString(R.string.welcome_message, "닉네임 없음")
                        showNickname(welcomeMessage)
                    }
                }
                .addOnFailureListener {
                    val welcomeMessage = getString(R.string.welcome_message, "닉네임 가져오기 실패")
                    showNickname(welcomeMessage)
                }
        }
    }

    // 닉네임을 화면에 표시하는 함수
    private fun showNickname(welcomeMessage: String?) {
        nicknameTextView.text = HtmlCompat.fromHtml(welcomeMessage ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

}