package com.example.toyproject

import android.app.Application
import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication: Application() {

    lateinit var service: RetrofitService

    override fun onCreate() {
        super.onCreate()

        createRetrofit()
    }

    fun createRetrofit() {

        // header 설정 (header에 token이 있는 retrofit)
        // 원래 나가려던 통신을 original에 잡아둠
        // original에 header 추가 -> proceed
        val header = Interceptor {
            val original = it.request()


            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("X-AUTH-TOKEN", "token $token")
                        .build()
                    it.proceed(request)
                }

            } else {
                it.proceed(original)
            }
        }


        // retrofit 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-15-164-93-46.ap-northeast-2.compute.amazonaws.com:8080/toy2/")
            .addConverterFactory(GsonConverterFactory.create())
            // .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)

    }


    // SharedPreferences에 token 값 저장되어 있음
    // 해당 key의 값(= token 값)을 불러옴
    // -> token 값이 없으면 login X
    private fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")

        return token != "null"
    }

    // token 값 내보내는 함수
    private fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")

        return if (token == "null") null
        else token
    }

}