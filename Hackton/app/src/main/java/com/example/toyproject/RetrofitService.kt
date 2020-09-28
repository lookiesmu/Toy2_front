package com.example.toyproject

import androidx.annotation.BoolRes
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    // @Headers("content-type: application/json")
    @POST("login/")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<HashMap<String, String>>

    @POST("users/signup")
    fun register(
        @Body user: HashMap<String, String>
    ): Call<HashMap<String, String>>

    @POST("users/")
    fun getNicknameIsExist(
        @Body nickname : HashMap<String, String>
    ): Call<HashMap<String, String>>


    @GET("quizzes/")
    fun getUserQuiz(): Call<QuizList>

    @POST("quizzes/")
    fun postUserQuiz(
        @Body quizList: QuizList
    ): Call<HashMap<String, String>>


    @GET("quizzes/{nickname}")
    fun getNicknameQuiz(
        @Path("nickname") nickname: String
    ): Call<QuizList>

    @POST("quizzes/{nickname}")
    fun postQuizScore(
        @Path("nickname") nickname: String,
        @Body params: HashMap<String, String>
    ): Call<HashMap<String, String>>

    @POST("users/rank/")
    fun postRanking(
        @Body params: HashMap<String, String>
    ): Call<RankList>

}