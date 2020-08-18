package com.example.hackaton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    var user_id=""
    var user_password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login : Button = findViewById(R.id.login_btn)
        val sign : TextView =findViewById(R.id.sign)

        var id_listener=EnterListener()
        var password_listener=EnterListener()

        id.setOnEditorActionListener(id_listener)

        pw.setOnEditorActionListener(password_listener)

        login.setOnClickListener {
            val intent= Intent(this@LoginActivity, CreateQuizActivity::class.java)
            startActivity(intent)
        }
        sign.setOnClickListener {
            val intent= Intent(this@LoginActivity, SignupActivity::class.java)
            intent.putExtra("user_id",user_id)
            intent.putExtra("user_password",user_password)
            startActivity(intent)
        }
    }

    inner class EnterListener: TextView.OnEditorActionListener{
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            user_id= id.text.toString()
            user_password=pw.text.toString()
            return false
        }
    }
}
