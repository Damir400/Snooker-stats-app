package com.example.myfirstapp.models

data class PlayerModel(
   var name : String = "",
   var globalScore: Int = 0,
   var historyFramePlayer: MutableList<Int> = mutableListOf(),
   var score: Int = 0
)