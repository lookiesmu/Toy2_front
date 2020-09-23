package com.example.hackaton

import android.provider.ContactsContract
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import org.json.JSONObject

interface RetrofitService {

////    @Headers("content-type: application/json")
////    @POST("login")
////    fun login(
////        @Body params : HashMap<String, String>
////    ):Call<User>
//
//    @Headers("content-type: application/json")
//    @POST("login")
//    fun register(
//        @Body params: HashMap<String, String>   // nickname, password
//    ): Call<User>
//
////    @FormUrlEncoded
////    @POST("/login")
////    fun login(
////        @Field("nickname")nickname:String,
////        @Field("password")password:String
////    ): Call<User>
////
///////////////////////////////////////////////////////
    // 회원가입 POST
//    @POST("users")
//    @FormUrlEncoded // Field를 하나하나 보낼 때 적어줘야 함
//    fun register(
//        // @Body register: Register // Body에 Register라는 객체를 넣어 api 요청
//        @Field("nickname") nickname: String,
//        @Field("password") password: String
//    ): Call<User>   // 응답으로 User 객체가 반환

    @Headers("content-type: application/json")
    @POST("users/signup")
    fun register(
        @Body user: HashMap<String,String>
    ): Call<Sign>

    // 닉네임 중복 확인 GET
    @Headers("content-type: application/json")
    @POST("users")
    fun getNicknameIsExist(
        //@Query("success") nickname: String
        @Body nickname : HashMap<String, String>
    ): Call<NickName>

    // 로그인 POST
    @Headers("content-type: application/json")
    @POST("login")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<Test>

}