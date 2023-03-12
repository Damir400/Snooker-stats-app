package com.example.myfirstapp.viewModels

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.BallType
import com.example.myfirstapp.Teams
import com.example.myfirstapp.models.HistoryModel
import com.example.myfirstapp.models.PlayerModel
import java.util.*

class SnookerViewModel(player1: PlayerViewModel, player2: PlayerViewModel) : ViewModel() {
//    private val _maxScore = 147

    private val _player1 = MutableLiveData<PlayerViewModel>()
    private val _player2 = MutableLiveData<PlayerViewModel>()

    var player1: MutableLiveData<PlayerViewModel> = _player1
    var player2: MutableLiveData<PlayerViewModel> = _player2

    private val _progress = MutableLiveData<MutableList<Pair<PlayerViewModel, Int>>>()
    var progress: LiveData<MutableList<Pair<PlayerViewModel, Int>>> = _progress

    private val _frameScore = MutableLiveData<String>()
    var frameScore: LiveData<String> = _frameScore

    private val _timeFrame = MutableLiveData<Int>()
    var timeFrame: LiveData<Int> = _timeFrame

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

    }

    // Отмена хода
    fun cancel() {
        if (!_progress.value?.isEmpty()!!) {
            var (player, score) = _progress.value!!.last()

            player.addScore(score)
            _progress.value!!.removeLast()
        }
    }

    // Добавление очков в счёт фреймов
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

    // обнуление значений для нового турнира
    fun newTournament(){
        player1.value!!.setScore(0)
        player2.value!!.setScore(0)
        _progress.value?.clear()

        player1.value!!.clearGlobalScore()
        player2.value!!.clearGlobalScore()

        player1.value!!.historyFramePlayer.value!!.clear()
        player2.value!!.historyFramePlayer.value!!.clear()

        frameScoreToString()

    }

    fun addHistoryPlayers(){
        player1.value!!.addHistoryFrame()
        player2.value!!.addHistoryFrame()
    }

    fun getHistoryModel(): HistoryModel {
        val datetime: String = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
        val playerModel1: PlayerModel = player1.value?.getPlayerModel()!!
        val playerModel2: PlayerModel = player2.value?.getPlayerModel()!!

        return HistoryModel(datetime, playerModel1, playerModel2)
    }


}