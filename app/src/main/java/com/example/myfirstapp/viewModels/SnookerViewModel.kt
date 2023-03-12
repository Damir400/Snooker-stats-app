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

class SnookerViewModel() : ViewModel() {
    val player1 = PlayerViewModel()
    val player2 = PlayerViewModel()

    var progress: LiveData<MutableList<Pair<PlayerViewModel, Int>>> = MutableLiveData()

    var frameScoreTitle: LiveData<String> = MutableLiveData()//_frameScore
    fun updateScoreTitle() {
        (frameScoreTitle as? MutableLiveData)?.value = "${player1.getGlobalScore()}:${player2.getGlobalScore()}"
    }

    fun getScoreTitle(defValue: String = "0:0") : String {
        val result = (frameScoreTitle as? MutableLiveData)?.value
        return if (result == null || result!!.isEmpty()) {
            defValue
        } else {
            result!!
        }
    }

    var frameTime: LiveData<Int> = MutableLiveData()

    init {
        (progress as? MutableLiveData)?.value = mutableListOf()
    }

    fun move(teamId: Teams, ballType: BallType = BallType.SNOOKER_RED){
        var curPlayer = player1

        if (teamId == Teams.TEAM_RIGHT) {
            curPlayer = player2
        }

        try {
            val score: Int? = curPlayer.balls.value?.get(ballType)?.points?.value
            if (score != null) {
                (progress as? MutableLiveData)?.value?.add(Pair(curPlayer, -score))
                curPlayer.addScore(score)
            }
        }
        catch (ex: java.lang.Exception) {
            println(ex.message)
        }
    }

    // Отмена хода
    fun cancel() {
        val currentProgress = (progress as? MutableLiveData)?.value

        if (currentProgress != null && currentProgress.isNotEmpty()) {
            val (player, score) = currentProgress.last()// .value!!.last()
            player.addScore(score)

            currentProgress.removeLast()
        }
    }

    // Добавление очков в счёт фреймов
    fun addGlobalScore (){
        if (player1.getScore(0) > player2.getScore(0)) {
            player1.changeGlobalScore(1)
        } else if (player2.getScore(0) > player1.getScore(0)) {
            player2.changeGlobalScore(1)
        }
        updateScoreTitle()

        player1.changeScore(0)
        player2.changeScore(0)

        (progress as? MutableLiveData)?.value?.clear()
    }

    // обнуление значений для нового турнира
    fun newTournament() {
        player1.changeScore(0)
        player1.changeGlobalScore(0)
        player1.initFramePoints()

        player2.changeScore(0)
        player2.changeGlobalScore(0)
        player2.initFramePoints()

        (progress as? MutableLiveData)?.value?.clear()

        updateScoreTitle()
    }

    fun addHistoryPlayers(){
        player1.pushFramePoint(player1.getScore())
        player2.pushFramePoint(player2.getScore())
    }

    fun getHistoryModel(): HistoryModel {
        val datetime: String = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
        val playerModel1: PlayerModel = player1.getPlayerModel()
        val playerModel2: PlayerModel = player2.getPlayerModel()

        return HistoryModel(datetime, playerModel1, playerModel2)
    }
}