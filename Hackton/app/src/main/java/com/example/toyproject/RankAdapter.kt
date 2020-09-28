package com.example.toyproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankAdapter(
    val rankList: ArrayList<Rank>,
    val inflater: LayoutInflater
):RecyclerView.Adapter<RankAdapter.RankViewHolder>() {
    inner class RankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ranking: TextView
        val rankNickname: TextView
        val rankScore: TextView

        init {
            ranking = itemView.findViewById(R.id.rank_item_ranking)
            rankNickname = itemView.findViewById(R.id.rank_item_nickname)
            rankScore = itemView.findViewById(R.id.rank_item_score)
        }

        fun bind(rank: Rank, index: Int) {
            var i = index
            ranking.text = (i+1).toString()
            rankNickname.text = rank.nickname
            rankScore.text = rank.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view = inflater.inflate(R.layout.rank_item, parent, false)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.bind(rankList[position], position)
    }

    override fun getItemCount(): Int {
        return rankList.size
    }
}