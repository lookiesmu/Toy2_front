package com.example.hackaton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_nickname.*

class NicknameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        user_button.setOnClickListener {
            val intent = Intent(this, MyifoActivity::class.java)
            startActivity(intent)
        }

        adress_edit_text.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //변화 된 후 Editable이라는게 들어옴 .
                Log.d("edit","afterTextChanged :" + p0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("edit","beforeTextChanged :" + p0)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("edit","onTextChanged :" + p0)
            }
        })

    }


}