package com.example.toyproject

import java.io.Serializable

data class RankList(
    val ranking: ArrayList<Rank>
): Serializable

data class Rank (
    val userId: Int = 0,
    val nickname: String = "",
    val score: Int = 0
): Serializable