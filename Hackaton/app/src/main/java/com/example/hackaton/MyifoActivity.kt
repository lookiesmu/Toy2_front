package com.example.hackaton

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.net.CacheResponse

class MyifoActivity : AppCompatActivity() {

    val rank_url = Constants.BASE_URL + "/users/rank"
    val quiz_url = Constants.BASE_URL + "/quizzes"
    val rankList: ArrayList<Rank> = arrayListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myifo)


    }

    inner class Mypage_Control {
        fun show_Rank(context: Context) {
            viewManager = LinearLayoutManager(context)

        }
    }

//    inner class asynctask : AsyncTask<String, Void, String>() {
//        var state = -1
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
////        override fun doInBackground(vararg params: String): String {
////            state = Integer.parseInt(params[0])
////            val url = params[1]
////            var response:String = ""
////            when(state){
////                0->{
////
////                }
////            }
////        }
////
////
////    }
//    }

}





