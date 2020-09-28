package com.example.toyproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var usernameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText
    lateinit var registerBtn: Button
    lateinit var check: Button
    var checkBtnClick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initView(this)

        check.setOnClickListener {
            nicknameCheck(this)
        }

        registerBtn.setOnClickListener {
            if(checkBtnClick == 0){
                Toast.makeText(this,"닉네임 중복확인을 해주세요",Toast.LENGTH_SHORT).show()
            }else
                register(this)
        }
    }

    private fun initView(activity: Activity) {
        usernameView = findViewById(R.id.signUp_id)
        userPassword1View = findViewById(R.id.signUp_pw1)
        userPassword2View = findViewById(R.id.signUp_pw2)
        registerBtn = findViewById(R.id.signUp_btn)
        check = findViewById(R.id.signUp_check)
    }

    // 회원가입하는 함수
    private fun register(activity: Activity) {
        val nickname = usernameView.text.toString()
        val password1 = userPassword1View.text.toString()
        val password2 = userPassword2View.text.toString()
        val user = HashMap<String,String>()

        if (nickname != "" && password1 != "" && password2 != "") {
            // 비밀번호 일치 확인
            if (password1 != password2) {
                Toast.makeText(activity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                user.put("nickname",nickname)
                user.put("password", password1)
                (application as MasterApplication).service.register(user)
                    .enqueue(object : Callback<HashMap<String, String>> {
                        override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                            Toast.makeText(activity, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<HashMap<String, String>>,
                            response: Response<HashMap<String, String>>
                        ) {
                            if (response.isSuccessful) {
                                val result = response.body()
                                if (result!!.get("signup") == "true") {
                                    Toast.makeText(activity, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show()
                                    val token = response.headers().get("X-AUTH-TOKEN").toString()
                                    saveUserToken(token, activity)  // Token 저장

                                    (application as MasterApplication).createRetrofit()
                                    activity.startActivity(Intent(activity, LoginActivity::class.java))

                                } else {
                                    Toast.makeText(activity, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }


                    })
            }
        } else {    // 회원가입시 공백이 있을 경우
            Toast.makeText(activity, "회원가입 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    // 사용자 토큰 저장하는 함수
    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.apply()
    }

    // 닉네임 중복확인하는 함수
    private fun nicknameCheck(activity: Activity) {
        val nickname = usernameView.text.toString()
        if (nickname == "") {
            Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            val body = HashMap<String, String>()
            body.put("nickname", nickname)

            (application as MasterApplication).service.getNicknameIsExist(body)
                .enqueue(object : Callback<HashMap<String, String>> {
                    override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                        Toast.makeText(activity, "회원가입 닉네임 중복 실패", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<HashMap<String, String>>,
                        response: Response<HashMap<String, String>>
                    ) {
                        if (response.isSuccessful) {
                            val result = response.body()

                            // 닉네임 중복 확인
                            if (result!!.get("success") == "false") {
                                Toast.makeText(activity, "사용 불가능한 닉네임입니다", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show()
                                checkBtnClick++
                            }
                        }
                    }
                })
        }
    }

}