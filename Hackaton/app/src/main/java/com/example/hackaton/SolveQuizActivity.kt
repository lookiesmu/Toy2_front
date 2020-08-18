package com.example.hackaton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_solve_quiz.*

class SolveQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solve_quiz)

        val quizList = ArrayList<Quiz>()

        for (i in 0 until 10) {
            quizList.add(Quiz("i번째 퀴즈", i))
        }


        val solveQuizAdapter = SolveQuizAdapter(quizList, LayoutInflater.from(this))
        solve_quiz_recyclerview.adapter = solveQuizAdapter
        solve_quiz_recyclerview.layoutManager = LinearLayoutManager(this)

    }
}

class SolveQuizAdapter (
    val quizList: ArrayList<Quiz>,
    val inflater: LayoutInflater
): RecyclerView.Adapter<SolveQuizAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val quizContent: TextView
        val yesBtn: Button
        val noBtn: Button

        init {
            quizContent = itemView.findViewById(R.id.solve_quiz)
            yesBtn = itemView.findViewById(R.id.solve_quiz_yes)
            noBtn = itemView.findViewById(R.id.solve_quiz_no)
        }

        // yesBtn 누르면 1, noBtn 누르면 0으로 변경
        fun set(quiz: Quiz, index: Int) {
            yesBtn.setOnClickListener {
                if (quiz.answer != 1) {
                    quiz.answer = 1
                } else {
                    quiz.answer = -1
                }
            }

            noBtn.setOnClickListener {
                if (quiz.answer != 0) {
                    quiz.answer = 0
                } else {
                    quiz.answer = -1
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.solve_quiz_recyclerview, parent, false)
        return ViewHolder(view)
    }

    // item 총 갯수 return
    override fun getItemCount(): Int {
        return quizList.size
    }

    // onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(quizList[position], position)
    }
}


class Quiz (val quizContent: String, var answer: Int) { }
