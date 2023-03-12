package com.example.myfirstapp.viewModels

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProgressViewModel : DialogFragment() {
//    private val _progress = MutableLiveData<MutableList<Pair<PlayerViewModel,Int>>>()
//    var progress: LiveData<MutableList<Pair<PlayerViewModel, Int>>> = _progress
//
//    private val _frameScore = MutableLiveData<String>()
//    var frameScore: LiveData<String> = _frameScore
//
//    init {
//        _progress.value = mutableListOf<Pair<PlayerViewModel, Int>>()
//    }

//    fun cancel() {
//        if (!_progress.value?.isEmpty()!!) {
//            var (player, score) = _progress.value!!.last()
//
//            player.addScore(score)
//            _progress.value!!.removeLast()
//        }
//    }
//
//    fun addGlobalScore (player1: PlayerViewModel, player2: PlayerViewModel){
//        if ((player1.score.value ?: 0) > (player2.score.value ?: 0)){
//            player1.addGlobalScorePlayer()
//        }
//        else if ((player1.score.value ?: 0) < (player2.score.value ?: 0)) {
//            player2.addGlobalScorePlayer()
//        }
//        frameScoreToString(player1, player2)
//
//        player1.setScore(0)
//        player2.setScore(0)
//        _progress.value?.clear()
//    }


//    fun move(player: PlayerViewModel, score: Int, ballType: BallType = BallType.SNOOKER_RED){
//        _progress.value?.add(Pair(player, -score))
//        player.addScore(score, ballType)
//
//        player.updateBalls(ballType)
//
//
//    }

//    fun move(player: PlayerViewModel, score: Int){
//        _progress.value?.add(Pair(player, -score))
//        player.addScore(score)
//        player.updateBalls()
//
//
//    }

//    fun frameScoreToString(player1: PlayerViewModel, player2: PlayerViewModel){
//        _frameScore.value = "${(player1.globalScore.value!!)}:${(player2.globalScore.value!!)}"
//    }



}