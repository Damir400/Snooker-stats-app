package com.example.myfirstapp.models

data class HistoryModel(
    val dateTime: String? = null,
    val player1: PlayerModel? = null,
    val player2: PlayerModel? = null,
//    fun setData(snookerViewModel: SnookerViewModel){
//    dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
//    player1 = snookerViewModel.player1.value!!.getPlayerModel()
//    player2 = snookerViewModel.player2.value!!.getPlayerModel()
//    }
)
//{
//    var dateTime = ""
//    var player1: PlayerModel? = null
//    var player2: PlayerModel? = null
//
//    fun setData(snookerViewModel: SnookerViewModel){
//        dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
//        player1 = snookerViewModel.player1.value!!.getPlayerModel()
//        player2 = snookerViewModel.player2.value!!.getPlayerModel()
//    }
//}