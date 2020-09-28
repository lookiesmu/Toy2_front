package com.example.toyproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val userNickname = intent.getStringExtra("userNickname").toString()

        search_enter_btn.setOnClickListener {
            val nickname = search_editText.text.toString()  // 퀴즈 주인 닉네임

            // 입력한 닉네임이 존재하지 않을 경우 추가해야 함
            
            if (nickname == "") {
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                // 입력한 닉네임이 존재할 경우
                startActivity(Intent(this, SolveQuizActivity::class.java)
                    .putExtra("nickname", nickname)             // 퀴즈 주인 닉네임 전달
                    .putExtra("userNickname", userNickname))    // 현재 로그인 상태인 유저 닉네임 전달
            }
        }

        // 내 정보 액티비티 클릭할 경우
        // 1) 생성한 퀴즈가 없을 경우: CreateQuizActivity로 전환
        // 2) 생성한 퀴즈가 있을 경우: RankActivity로 전환
        search_next_btn.setOnClickListener {
            search_btn.setImageResource(R.drawable.search_btn_no)
            search_next_btn.setImageResource(R.drawable.search_next_yes)
            startActivity(Intent(this, CreateQuizActivity::class.java)
                .putExtra("userNickname", userNickname))    // 현재 로그인 상태인 유저 닉네임 전달
        }

    }
}