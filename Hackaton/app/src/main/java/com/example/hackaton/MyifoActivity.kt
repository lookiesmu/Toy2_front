package com.example.hackaton

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyifoActivity : AppCompatActivity() {

    val rank_url = Constants.BASE_URL + "/users/rank"
    val quiz_url = Constants.BASE_URL + "/quizzes"

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myifo)


    }

    inner class Mypage_Control {
        fun show_Rank(context: Context){
            viewManager = LinearLayoutManager(context)

        }
    }




}

