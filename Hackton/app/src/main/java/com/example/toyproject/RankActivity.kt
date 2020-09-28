package com.example.toyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_rank.*
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        val nickname = intent.getStringExtra("nickname").toString()     // 퀴즈 주인 닉네임
        val userNickname = intent.getStringExtra("userNickname").toString()     // 현재 로그인 상태인 유저 닉네임
        rank_nick.setText(nickname).toString()

        val params = HashMap<String, String>()
        params.put("nickname", nickname)

        (application as MasterApplication).service.postRanking(params)
            .enqueue(object : Callback<RankList> {
            override fun onFailure(call: Call<RankList>, t: Throwable) {
                Toast.makeText(this@RankActivity, "랭킹 조회 실패", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onResponse(call: Call<RankList>, response: Response<RankList>) {
                if (response.isSuccessful) {
                    val rankListList = response.body()
                    val rankList = rankListList!!.ranking

                    val rankAdapter = RankAdapter(rankList, LayoutInflater.from(this@RankActivity))
                    rank_recyclerview.adapter = rankAdapter
                    rank_recyclerview.layoutManager = LinearLayoutManager(this@RankActivity)

                    search_btn2.setOnClickListener {
                        search_btn2.setImageResource(R.drawable.search_btn_yes)
                        search_next_btn2.setImageResource(R.drawable.search_next_no)
                        startActivity(Intent(this@RankActivity, SearchActivity::class.java)
                                .putExtra("userNickname", userNickname))
                    }

                }
            }
        })

    }
}