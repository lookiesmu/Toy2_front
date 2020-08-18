package com.example.hackaton

import java.io.Serializable

class Quiz_info (
    var quizId: Int,
    var content: String,
    var answer: Int
) : Serializable{
    constructor():this(0,"",0)
}