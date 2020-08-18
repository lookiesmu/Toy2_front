package com.example.hackaton

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    //baseURL뒷부분만 적어주면 된다.
    @GET("/login")
    //retrofit2 의 Call<>안에 넣어준다.
    fun getList(): Call<ArrayList<User>>

   @POST("/login")
    fun createStudentEasy(
    @Body person : User
    ): Call<User>

    @FormUrlEncoded
    fun register(
        @Field("username")username:String,
        @Field("password1")password1:String,
        @Field("password2")password2:String
    ): Call<User>

    @FormUrlEncoded
    fun login(
        @Field("username")username:String,
        @Field("password")password:String
    ): Call<User>
}