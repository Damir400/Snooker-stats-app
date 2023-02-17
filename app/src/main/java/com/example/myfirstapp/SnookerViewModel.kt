package com.example.myfirstapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SnookerViewModel(player1: PlayerViewModel, player2: PlayerViewModel) {
//    private val _maxScore = 147

    private val _player1 = MutableLiveData<PlayerViewModel>()
    private val _player2 = MutableLiveData<PlayerViewModel>()

    var player1: MutableLiveData<PlayerViewModel> = _player1
    var player2: MutableLiveData<PlayerViewModel> = _player2

    private val _progress = MutableLiveData<MutableList<Pair<PlayerViewModel, Int>>>()
    var progress: LiveData<MutableList<Pair<PlayerViewModel, Int>>> = _progress

    private val _frameScore = MutableLiveData<String>()
    var frameScore: LiveData<String> = _frameScore

    init {
        _progress.value = mutableListOf()
        _player1.value = player1
        _player2.value = player2
    }

    fun move(teamId: Teams, ballType: BallType = BallType.SNOOKER_RED){
        var curPlayer = _player1.value
        var nexPlayer = _player2.value

        if(teamId == Teams.TEAM_RIGHT) {
            curPlayer = _player2.value
            nexPlayer = _player1.value
        }
        val score = curPlayer!!.balls.value!![ballType]!!.points.value!!
        _progress.value?.add(Pair(curPlayer, -score))
        curPlayer.addScore(score)

//        curPlayer!!.updateBallsState(ballType)
//        nexPlayer!!.updateBallsState(ballType, false)
    }

    fun cancel() {
        if (!_progress.value?.isEmpty()!!) {
            var (player, score) = _progress.value!!.last()

            player.addScore(score)
            _progress.value!!.removeLast()
        }
    }

    fun addGlobalScore (){
        if ((player1.value!!.score.value ?: 0) > (player2.value!!.score.value ?: 0)){
            player1.value!!.addGlobalScorePlayer()
        }
        else if ((player1.value!!.score.value ?: 0) < (player2.value!!.score.value ?: 0)) {
            player2.value!!.addGlobalScorePlayer()
        }
        frameScoreToString()

        player1.value!!.setScore(0)
        player2.value!!.setScore(0)
        _progress.value?.clear()
    }

    fun frameScoreToString(){
        _frameScore.value = "${(player1.value!!.globalScore.value!!)}:${(player2.value!!.globalScore.value!!)}"
    }
}