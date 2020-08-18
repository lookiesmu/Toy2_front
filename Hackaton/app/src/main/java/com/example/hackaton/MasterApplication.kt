package com.example.hackaton

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication:Application() {
    lateinit var service : RetrofitService

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        createRetrofit()
        //chrome://inspect/#devices
    }

    fun createRetrofit(){
        //app에서 나가는 통신을 가로챔( interceptor )
        val header= Interceptor{
            //original에서 개조 ( 개조 내용은 header를 달아주는 것 )
            val original=it.request()

            if(checkIsLogin()){
                //let : null이 아니면 괄호 안 내용을 실행
                getUserToken()?.let {token ->
                    val requeset=original.newBuilder()
                        .header("Authorization","token "+token)
                        .build()
                    //개조한걸 진행 ( 내보내는 것 )
                    it.proceed(requeset)
                }

            }else{
                it.proceed(original)
            }
        }

        val client= OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service=retrofit.create(RetrofitService::class.java)
    }

    fun checkIsLogin():Boolean{
        val sp=getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp","null")
        if(token!="null") return true
        else return false
    }

    fun getUserToken():String?{
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp","null")
        if(token=="null") return null
        else return token
    }
}