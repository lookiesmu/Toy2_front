package com.example.toyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_solve_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolveQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solve_quiz)

        val nickname = intent.getStringExtra("nickname").toString()     // 현재 풀고있는 퀴즈 주인 닉네임
        val userNickname = intent.getStringExtra("userNickname").toString()     // 현재 로그인 상태인 유저 닉네임

        lateinit var quizList: ArrayList<Quiz>      // 풀 퀴즈 리스트
        var quizAnswerList = arrayListOf<Int>()     // 퀴즈 주인 답 리스트
        var myAnswerList = arrayListOf(-1, -1, -1, -1, -1)  // 내가 푼 답 리스트

        lateinit var result: String     // 퀴즈를 이미 푼 유저인지 확인하는 변수
                                        // 이미 푼 유저라면 "false", 처음 푼 유저라면 "true" 리턴

        // 현재 풀고있는 퀴즈 주인 닉네임 설정
        solve_nick.setText(nickname).toString()

        // 해당 닉네임의 퀴즈 불러옴
        (application as MasterApplication).service.getNicknameQuiz(nickname)
            .enqueue(object: Callback<QuizList> {
                override fun onFailure(call: Call<QuizList>, t: Throwable) {
                    Toast.makeText(this@SolveQuizActivity, "Quiz 실패", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onResponse(
                    call: Call<QuizList>,
                    response: Response<QuizList>
                ) {
                    if (response.isSuccessful) {
                        val quizListList = response.body()
                        quizList = quizListList!!.quizList      // quizList 받아옴
                        Toast.makeText(this@SolveQuizActivity, "퀴즈를 풀어주세요~", Toast.LENGTH_SHORT).show()

                        // 퀴즈 풀기 화면 뷰 작성
                        val solveQuizAdapter = QuizAdapter(quizList, LayoutInflater.from(this@SolveQuizActivity), myAnswerList)
                        solve_recyclerview.adapter = solveQuizAdapter
                        solve_recyclerview.layoutManager = LinearLayoutManager(this@SolveQuizActivity)

                        // 제출 버튼을 클릭했을 경우
                        solve_btn.setOnClickListener {
                            if (-1 in myAnswerList) {   // 풀지 않은 퀴즈가 있을 경우 toast
                            Toast.makeText(this@SolveQuizActivity, "풀지 않은 문제가 있습니다", Toast.LENGTH_SHORT).show()
                            } else {    // 모든 퀴즈를 풀었을 경우 dialog
                                for (i in 0 until 5) {
                                    quizAnswerList.add(i, quizList[i].answer)   // 퀴즈 주인 답 리스트 초기화
                                }
                                val score = calcScore(quizAnswerList, myAnswerList).toString()     // 퀴즈 score 계산

                                // (application as MasterApplication).createRetrofit()

                                // answerer: 현재 로그인 되어있는 사용자 (퀴즈를 푼 사용자)
                                // nickname: 퀴즈의 주인
                                val params = HashMap<String, String>()
                                params.put("answerer", userNickname)
                                params.put("score", score)

                                // 서버에 해당 스코어를 전송하고 결과 상태값 리턴
                                (application as MasterApplication).service.postQuizScore(nickname, params)
                                    .enqueue(object :Callback<HashMap<String, String>>{
                                        override fun onResponse(
                                            call: Call<HashMap<String, String>>,
                                            response: Response<HashMap<String, String>>
                                        ) {
                                            if (response.isSuccessful) {
                                                val body = response.body()
                                                result = body!!.get("success").toString()

                                                if (result == "true") {
                                                    setDialog(score, nickname, userNickname)    // 유저가 퀴즈를 처음 풀었을 때
                                                } else {
                                                    setFailDialog(nickname, userNickname)     // 유저가 이미 퀴즈를 풀었을 때
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                                            Toast.makeText(this@SolveQuizActivity, "해당 스코어 전송 실패", Toast.LENGTH_LONG).show()
                                            finish()
                                        }
                                    })
                            }
                        }

                    }
                }
            })

    }

    // score 계산하는 함수
    private fun calcScore(quizAnswerList: ArrayList<Int>, myAnswerList: ArrayList<Int>): Int {
        var score = 0

        for (i in 0 until quizAnswerList.size) {
            if (quizAnswerList[i] == myAnswerList[i])
                score += 20
        }
        return score
    }

    // 유저가 퀴즈를 처음 풀었을 때 사용되는 dialog 설정하는 함수
    private fun setDialog(score: String, nickname: String, userNickname: String) {
        val builder = AlertDialog.Builder(this@SolveQuizActivity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

        // dialog score 변경
        val dialogScore = dialogView.findViewById<TextView>(R.id.solve_score)
        dialogScore.setText("$score 점").toString()
        builder.setView(dialogView).show()

        val okBtn = dialogView.findViewById<Button>(R.id.solve_dialog_btn)
        okBtn.setOnClickListener {
            startActivity(Intent(this@SolveQuizActivity, RankActivity::class.java)
                .putExtra("nickname", nickname)
                .putExtra("userNickname", userNickname))
        }

    }

    // 유저가 이미 퀴즈를 풀었을 때 사용되는 dialog 설정하는 함수
    fun setFailDialog(nickname: String, userNickname: String) {
        val builder = AlertDialog.Builder(this@SolveQuizActivity)
        val dialogView = layoutInflater.inflate(R.layout.dialog_fail_view, null)
        builder.setView(dialogView).show()

        val okBtn = dialogView.findViewById<Button>(R.id.solve_dialog_fail_btn)
        okBtn.setOnClickListener {
            startActivity(Intent(this@SolveQuizActivity, RankActivity::class.java)
                .putExtra("nickname", nickname)
                .putExtra("userNickname", userNickname))
        }
    }

}