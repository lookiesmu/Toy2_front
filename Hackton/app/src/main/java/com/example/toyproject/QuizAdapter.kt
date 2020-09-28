package com.example.toyproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter (
    val quizList: ArrayList<Quiz>,
    val inflater: LayoutInflater,
    var myAnswerList: ArrayList<Int>
): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = inflater.inflate(R.layout.quiz_item, parent, false)
        return QuizViewHolder(view)
    }

    // item 총 갯수 return
    override fun getItemCount(): Int {
        return quizList.size
    }

    // onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터 연결
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(quizList[position], position)
    }

    inner class QuizViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val quizContent: TextView
        val yesBtn: ImageView
        val noBtn: ImageView

        init {
            quizContent = itemView.findViewById(R.id.quiz_content)
            yesBtn = itemView.findViewById(R.id.quiz_yes)
            noBtn = itemView.findViewById(R.id.quiz_no)
        }

        fun bind (quiz: Quiz, index: Int) {
            quizContent.text = quiz.content     // quiz 내용 설정

            yesBtn.setOnClickListener {
                yesBtn.setImageResource(R.drawable.solve_o_yes)
                noBtn.setImageResource(R.drawable.solve_x_no)
                myAnswerList[index] = 1     // 답 1로 변경
            }

            noBtn.setOnClickListener {
                noBtn.setImageResource(R.drawable.solve_x_yes)
                yesBtn.setImageResource(R.drawable.solve_o_no)
                myAnswerList[index] = 0     // 답 0으로 변경
            }
        }
    }
}

