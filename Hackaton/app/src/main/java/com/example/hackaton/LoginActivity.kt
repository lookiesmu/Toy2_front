package com.example.hackaton

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import com.example.hackaton.Json
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    var user_id = ""
    var user_password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login: Button = findViewById(R.id.login_btn)
        val sign: TextView = findViewById(R.id.sign)

        var id_listener = EnterListener()
        var password_listener = EnterListener()

        id.setOnEditorActionListener(id_listener)
        pw.setOnEditorActionListener(password_listener)

        login.setOnClickListener {
            val user_id: String = id.text.toString()
            val user_pw: String = pw.text.toString()

            (application as MasterApplication).service.login(
                user_id, user_pw
            ).enqueue(object: Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@LoginActivity,"error",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        val user = response.body()
                        val id=user!!.username
                        val token = user!!.token!!
                        saveUserToken(token, this@LoginActivity)
                        (application as MasterApplication).createRetrofit()
                        Toast.makeText(this@LoginActivity, "로그인하셨습니다.", Toast.LENGTH_SHORT)
                        startActivity(
                            Intent(this@LoginActivity, MyifoActivity::class.java)
                        )
                    }
                }
            })
        }
        sign.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("user_password", user_password)
            startActivity(intent)
        }
    }

    inner class EnterListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            user_id = id.text.toString()
            user_password = pw.text.toString()
            return false
        }
    }
}

fun saveUserToken(token:String, activity: Activity){
    val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
    val editor=sp.edit()
    editor.putString("login_sp", token)
    editor.commit()
}