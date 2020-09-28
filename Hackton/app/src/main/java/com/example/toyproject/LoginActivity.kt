package com.example.toyproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_signup_btn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        login_btn.setOnClickListener {
            val nickname = login_id.text.toString()
            val password = login_pw.text.toString()

            if (nickname == "") {
                Toast.makeText(this@LoginActivity, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (password == "") {
                Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val body = HashMap<String, String>()    // 닉네임, 패스워드 받아옴
                body.put("nickname", nickname)
                body.put("password", password)

                (application as MasterApplication).service.login(body)
                    .enqueue(object : Callback<HashMap<String, String>> {
                        override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                        override fun onResponse(
                            call: Call<HashMap<String, String>>,
                            response: Response<HashMap<String, String>>
                        ) {
                            if (response.isSuccessful) {
                                val user = response.body()
                                val token = response.headers().get("X-AUTH-TOKEN").toString()

                                if (token == "null") {
                                    Toast.makeText(this@LoginActivity, "아이디, 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show()
                                } else{
                                    saveUserToken(token, this@LoginActivity)

                                    (application as MasterApplication).createRetrofit()

                                    // 로그인 후 첫 화면으로 전환
                                    Toast.makeText(this@LoginActivity, "${user!!.get("nickname")}" + "님 환영합니다!" , Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@LoginActivity, SearchActivity::class.java)
                                        .putExtra("userNickname",user!!.get("nickname")))
                                }

                            }
                        }
                    }
                    )
            }
        }
    }

    // 사용자 토큰 저장하는 함수
    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.apply()
    }

}