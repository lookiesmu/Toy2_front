package com.example.toyproject

import java.io.Serializable

data class QuizList (
    val nickname: String = "",
    val quizList: ArrayList<Quiz>
): Serializable

data class Quiz (
    val id: Int = 0,
    val content: String = "",
    var answer: Int = 0,
): Serializable