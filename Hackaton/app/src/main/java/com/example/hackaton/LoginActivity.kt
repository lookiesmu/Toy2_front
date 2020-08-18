package com.example.hackaton

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import com.example.hackaton.Json
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign.setOnClickListener {
            startActivity(
                Intent(this@LoginActivity, MyifoActivity::class.java)
            )
        }

        login_button11.setOnClickListener {
            val nickname = id.text.toString()
            val password = pw.text.toString()
            val body = HashMap<String, String>()
            body.put("nickname", nickname)
            body.put("password", password)
            (application as MasterApplication).service.register(
                body
            ).enqueue(object : Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("user", call.toString(), t)
                    Toast.makeText(this@LoginActivity, "로그인에 실패했습니다", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        val token = response.headers().get("Authorization").toString()
                        Log.d("user", token)
                        Log.d("User", user.toString())
                        saveUserToken(token, this@LoginActivity)
                        (application as MasterApplication).createRetrofit()
                        Toast.makeText(this@LoginActivity, "환영합니다!", Toast.LENGTH_LONG).show()
                        startActivity (
                            // Intent(this@SignInActivity, SearchActivity::class.java)
                            Intent(this@LoginActivity, MyifoActivity::class.java)
                        )
                    }
                }
            })

        }
    }

    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_token", token)
        editor.commit()
    }
}