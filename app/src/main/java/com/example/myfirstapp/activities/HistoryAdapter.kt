package com.example.myfirstapp.activities
import com.example.myfirstapp.databinding.HistoryListItemBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.models.HistoryModel

class HistoryAdapter : ListAdapter<HistoryModel, HistoryAdapter.HistoryHolder>(ItemComparator()) {
    private var users: List<HistoryModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder.create(parent)
    }

    override fun onBindViewHolder(viewHolder: HistoryHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class HistoryHolder(private val binding: HistoryListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(historyModel: HistoryModel) = with(binding){

            timeMatch.text = historyModel.dateTime
            name1.text = "${historyModel.player1?.name}"
            name2.text = "${historyModel.player2?.name}"
            globalScore1.text = "(${historyModel.player1?.globalScore})"
            globalScore2.text = "(${historyModel.player2?.globalScore})"
            score1.text = "(${historyModel.player1?.historyFramePlayer!!.joinToString(separator = "; ")})"
            score2.text = "(${historyModel.player2?.historyFramePlayer!!.joinToString(separator = "; ")})"

        }

        companion object {
            fun create(parent: ViewGroup): HistoryHolder {
                return HistoryHolder(HistoryListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<HistoryModel>() {
        override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

        override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

    }

}