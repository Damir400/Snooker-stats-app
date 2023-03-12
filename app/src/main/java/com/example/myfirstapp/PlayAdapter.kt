//package com.example.myfirstapp
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Adapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.myfirstapp.databinding.ActivityMainBinding
//import com.example.myfirstapp.databinding.HistoryListItemBinding
//import kotlinx.android.synthetic.main.history_list_item.*
//
//class PlayAdapter: Adapter<SnookerViewModel, PlayAdapter.PlayHolder> {
//    private var snookerViewModel = SnookerViewModel(PlayerViewModel("player1"), PlayerViewModel("player2"))
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayAdapter.PlayHolder {
//        return PlayHolder.create(parent)
//    }
//
//    override fun onBindViewHolder(viewHolder: PlayAdapter.PlayHolder, position: Int) {
//        viewHolder.bind(getItem(position))
//    }
//
//    class PlayHolder(private val binding: ActivityMainBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(mainActivity: MainActivity) = with(binding){
//
//            timerGame.text = mainActivity.currentTime.toString()
//
//
//
//            timeMatch.text = historyModel.dateTime
//            name1.text = "${historyModel.player1?.name}"
//            name2.text = "${historyModel.player2?.name}"
//            globalScore1.text = "(${historyModel.player1?.globalScore})"
//            globalScore2.text = "(${historyModel.player2?.globalScore})"
//            score1.text = "(${historyModel.player1?.historyFramePlayer!!.joinToString(separator = "; ")})"
//            score2.text = "(${historyModel.player2?.historyFramePlayer!!.joinToString(separator = "; ")})"
//        }
//
//        companion object {
//            fun create(parent: ViewGroup): PlayHolder {
//                return PlayAdapter.PlayHolder(
//                    HistoryListItemBinding
//                        .inflate(LayoutInflater.from(parent.context), parent, false)
//                )
//            }
//        }
//    }
//
//    class ItemComparator : DiffUtil.ItemCallback<HistoryModel>() {
//        override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
//            return oldItem.dateTime == newItem.dateTime
//        }
//
//        override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
//            return oldItem.dateTime == newItem.dateTime
//        }
//
//    }
//
//}