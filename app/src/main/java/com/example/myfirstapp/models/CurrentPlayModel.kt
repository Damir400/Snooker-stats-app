package com.example.myfirstapp.models

data class CurrentPlayModel (
    var currentTimer: Int = 0,
    var player1: PlayerModel? = null,
    var player2: PlayerModel? = null,
)