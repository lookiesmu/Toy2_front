package com.example.hackaton

import android.provider.ContactsContract
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import org.json.JSONObject

interface RetrofitService {

//    @Headers("content-type: application/json")
//    @POST("login")
//    fun login(
//        @Body params : HashMap<String, String>
//    ):Call<User>

    @Headers("content-type: application/json")
    @POST("login")
    fun register(
        @Body params: HashMap<String, String>   // nickname, password
    ): Call<User>

//    @FormUrlEncoded
//    @POST("/login")
//    fun login(
//        @Field("nickname")nickname:String,
//        @Field("password")password:String
//    ): Call<User>
//

}