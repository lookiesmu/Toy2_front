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

        //Stetho.initializeWithDefaults(this)
        createRetrofit()  //사용자 로그인 여부에 의해 retrofit을 만들어냄 (토큰 있, 없)
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
                           // name : 서버가 설정한거 보고 다시 작성, value : 서버가 토큰 설정한거 보고 작성
                        .header("X-AUTH-TOKEN", "token $token")
                        .build()
                    //개조한걸 진행 ( 내보내는 것 )
                    it.proceed(requeset)
                }

            }else{
                it.proceed(original)
            }
        }

//        val client= OkHttpClient.Builder()
//            .addInterceptor(header)
//            .addNetworkInterceptor(StethoInterceptor())
//            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl("http://ec2-15-164-93-46.ap-northeast-2.compute.amazonaws.com:8080/toy2/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build()

        service=retrofit.create(RetrofitService::class.java)
    }


    // 로그인 확인
    // sharedPreference에 토큰 값이 있으면 로그인 된 것으로 간주, 없으면 로그인 안된 것으로 간주
    fun checkIsLogin():Boolean{
        val sp=getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val token = sp.getString("login_token","null")
        if(token!="null") return true

        return false
    }

    // 토큰 값을 내보냄
    // 로그인이 안된 경우 null을 내보내야 하므로 nullable
    fun getUserToken():String?{
        val sp = getSharedPreferences("login_token", Context.MODE_PRIVATE)
        val token = sp.getString("login_token","null")
        if(token=="null") return null
        else return token
    }
}