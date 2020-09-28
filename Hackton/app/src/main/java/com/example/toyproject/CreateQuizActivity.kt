package com.example.toyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateQuizActivity : AppCompatActivity() {

    lateinit var quizList: ArrayList<Quiz>      // 생성할 퀴즈 리스트
    var myAnswerList = arrayListOf(-1, -1, -1, -1, -1)  // 내가 푼 답 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        val userNickname = intent.getStringExtra("userNickname").toString()     // 현재 로그인 상태인 유저 닉네임

        create_nick.setText(userNickname).toString()

        (application as MasterApplication).service.getUserQuiz().enqueue(object : Callback<QuizList>{
            override fun onFailure(call: Call<QuizList>, t: Throwable) {
                Toast.makeText(this@CreateQuizActivity, "Quiz 실패", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onResponse(call: Call<QuizList>, response: Response<QuizList>) {
                if (response.isSuccessful) {
                    var quizListList = response.body()
                    quizList = quizListList!!.quizList
                    Toast.makeText(this@CreateQuizActivity, "퀴즈를 만들어주세요~", Toast.LENGTH_SHORT).show()

                    // 퀴즈 생성하기 화면 뷰 작성
                    val createQuizAdapter = QuizAdapter(quizList, LayoutInflater.from(this@CreateQuizActivity), myAnswerList)
                    create_recyclerview.adapter = createQuizAdapter
                    create_recyclerview.layoutManager = LinearLayoutManager(this@CreateQuizActivity)

                    create_btn.setOnClickListener {
                        if (-1 in myAnswerList) {   // 풀지 않은 퀴즈가 있을 경우 toast
                            Toast.makeText(this@CreateQuizActivity, "풀지 않은 문제가 있습니다", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0 until 5) {
                                quizList[i].answer = myAnswerList[i]
                            }

                            // nickname, quizList 담긴 객체 생성
                            // 생성한 객체 서버로 post

                        }
                    }

                }
            }


        })

    }
}