package com.example.hackaton

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CreateQuizActivity : AppCompatActivity() {

    val createQuizList: ArrayList<Quiz_info> = arrayListOf()

    private lateinit var recyclerView:RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)


    }

    inner class CreateQuiz_Control{
        fun GET_CreateQuiz(){
            val url = getString(R.string.server_url)+"/quizzes"

        }
        fun POST_CreateQuiz(){
            val url = getString(R.string.server_url)+"/quizzes"
        }
        fun show_Quiz(context:Context){
            viewManager = LinearLayoutManager(context)

        }

    }
}


