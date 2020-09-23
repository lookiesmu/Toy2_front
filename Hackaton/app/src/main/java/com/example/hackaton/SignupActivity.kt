package com.example.hackaton

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class SignupActivity : AppCompatActivity() {

    lateinit var usernameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText
    lateinit var registerBtn: Button
    lateinit var check: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initView(this@SignupActivity)

        check.setOnClickListener {
            // 닉네임 중복 확인
            nicknameCheck(this)
        }

        registerBtn.setOnClickListener {
            register(this)
        }
    }

    fun initView(activity: Activity) {
        usernameView = findViewById(R.id.sign_id)
        userPassword1View = findViewById(R.id.sign_pw)
        userPassword2View = findViewById(R.id.sign_pw2)
        registerBtn = findViewById(R.id.sign_btn)
        check = findViewById(R.id.check_up)
    }

    fun register(activity: Activity) {
        val nickname = getNickName()
        val password1 = getPassword1()
        val password2 = getPassword2()
        val user = HashMap<String,String>()


        if (nickname != "" && password1 != "" && password2 != "") {
            // 비밀번호 일치 확인
            if (password1 != password2) {
                Toast.makeText(activity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
            } else {
                user.put("nickname",nickname)
                user.put("password", password1)
                (application as MasterApplication).service.register(
                    user
                ).enqueue(object : Callback<Sign> {
                        override fun onFailure(call: Call<Sign>, t: Throwable) {
                            Toast.makeText(activity, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Sign>, response: Response<Sign>) {

                                val ok = response.body()
                                Log.d("tesT",ok?.signup.toString())
                                if(ok?.signup=="true") {
                                    Toast.makeText(activity, "회원가입에 성공했습니다", Toast.LENGTH_LONG)
                                        .show()


                                    val token = response.headers().get("X-AUTH-TOKEN").toString()
                                    saveUserToken(token, activity)
                                    (application as MasterApplication).createRetrofit()
                                    activity.startActivity(
                                        Intent(activity, LoginActivity::class.java)
                                    )
                                }else{
                                    Toast.makeText(activity, "회원가입에 실패했습니다3", Toast.LENGTH_LONG).show()
                                }
                                Toast.makeText(activity, "회원가입에 실패했습니다2", Toast.LENGTH_LONG).show()

                        }
                    })
            }
        } else {
            Toast.makeText(activity, "회원가입 정보를 입력해주세요", Toast.LENGTH_LONG).show()
        }
    }

    // 닉네임 중복 확인 함수
    fun nicknameCheck(activity: Activity) {
        Log.d("dup","fun ok")
        val nickname = getNickName()
        if (nickname == "") {
            Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_LONG).show()
        } else {
            Log.d("dup",nickname)
            val body = HashMap<String, String>()
            body.put("nickname", nickname)
           // body.put("password", "")

            (application as MasterApplication).service.getNicknameIsExist(body)
                .enqueue(object : Callback<NickName> {
                    override fun onFailure(call: Call<NickName>, t: Throwable) {
                        Log.d("internet1","no internet")
                        Toast.makeText(activity, "닉네임 중복 확인에 실패했습니다", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<NickName>, response: Response<NickName>) {
                        //Log.d("internet","onResponse1")
                        //Log.d("internet",response.isSuccessful.toString())
                        Log.d("internet", response.toString())
                        if (response.isSuccessful) {
                            //Log.d("internet","onResponse2")
                            val result = response.body()

                            val success = result?.success
                            //Log.d("dup",id)
                            Log.d("dup",response.toString())
                            // 닉네임 중복
                            if (success=="false") {
                                Toast.makeText(activity, "사용 불가능한 닉네임입니다", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity, "사용 가능한 닉네임입니다", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        }

    }

    // 토큰 받아서 SharedPreference에 저장
    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.apply()
    }

    fun getNickName(): String {
        return usernameView.text.toString()
    }

    fun getPassword1(): String {
        return userPassword1View.text.toString()
    }

    fun getPassword2(): String {
        return userPassword2View.text.toString()
    }
}

