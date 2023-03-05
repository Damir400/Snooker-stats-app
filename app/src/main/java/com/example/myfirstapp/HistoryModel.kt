package com.example.myfirstapp

import android.icu.text.SimpleDateFormat
import java.util.*

class HistoryModel {
    val history: MutableMap<String, Pair<PlayerModel, PlayerModel>> = mutableMapOf()

    fun addHistory(snookerViewModel: SnookerViewModel){
        val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
        var player1 = snookerViewModel.player1.value!!.getPlayerModel()
        var player2 = snookerViewModel.player2.value!!.getPlayerModel()

        history[dateTime] = (Pair(player1, player2))
    }
}