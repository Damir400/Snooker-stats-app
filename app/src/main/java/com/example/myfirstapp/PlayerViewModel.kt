package com.example.myfirstapp

import android.os.Parcelable
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import kotlinx.parcelize.Parcelize

class PlayerViewModel(name: String) {
    private val _name = MutableLiveData<String>()
    var name: LiveData<String> = _name


    private val maxScore = 147
    private var _score = MutableLiveData(0)
    var score: LiveData<Int> = _score

    private var _globalScore = MutableLiveData(0)
    var globalScore: LiveData<Int> = _globalScore

    private val _balls = MutableLiveData<MutableMap<BallType,SnookerBallViewModel>>()
    var balls: LiveData<MutableMap<BallType,SnookerBallViewModel>> = _balls

    private var _historyFramePlayer = MutableLiveData<MutableList<Int>>()
    var historyFramePlayer: LiveData<MutableList<Int>> = _historyFramePlayer


    init {
        _name.value = name
        _historyFramePlayer.value = mutableListOf()
//        _name.value = playerName
        _balls.value = mutableMapOf(
            BallType.SNOOKER_RED to SnookerBallViewModel(1, 5, BallType.SNOOKER_RED)
//                    BallType.SNOOKER_RED to SnookerBallViewModel(1, 15, BallType.SNOOKER_RED)
            ,BallType.SNOOKER_YELLOW to SnookerBallViewModel(2, 1, BallType.SNOOKER_YELLOW)
            ,BallType.SNOOKER_GREEN to SnookerBallViewModel(3,1, BallType.SNOOKER_GREEN)
            ,BallType.SNOOKER_BROWN to SnookerBallViewModel(4,1, BallType.SNOOKER_BROWN)
            ,BallType.SNOOKER_BLUE to SnookerBallViewModel(5,1, BallType.SNOOKER_BLUE)
            ,BallType.SNOOKER_PURPLE to SnookerBallViewModel(6,1, BallType.SNOOKER_PURPLE)
            ,BallType.SNOOKER_BLACK to SnookerBallViewModel(7,1, BallType.SNOOKER_BLACK))

        _balls.value!!.forEach { item ->
            item.value.update(true)
        }

        _balls.value!![BallType.SNOOKER_RED]!!.update(true)
    }

//    fun addScore(ballType: BallType = BallType.SNOOKER_RED) {
//        _score.value = (_score.value ?: 0) + _balls.value!![ballType]!!.points.value!!
//    }

    fun addScore(addedScore: Int) {
        if (isMax(addedScore)) {
            setScore(maxScore)
        }
        else {
            _score.value = (_score.value ?: 0) + addedScore
        }

//        _balls.value!!.last().update(false)
    }

    fun updateBallsState(ballType: BallType, isCurrentPlayer: Boolean = true) {
        val ball = _balls.value!![ballType]!!
        if (isCurrentPlayer) {
            if (_balls.value!![BallType.SNOOKER_RED]!!.count.value!! > 0) {
                if (ball.ballType.value == BallType.SNOOKER_RED) {
                    ball.count.value = ball.count.value!! - 1

                    _balls.value!!.forEach { item ->
                        item.value.update(true)
                    }
                    ball.update(false)
                }
                else {
                    _balls.value!!.forEach { item ->
                        item.value.update(false)
                    }
                    _balls.value!![BallType.SNOOKER_RED]!!.update(true)
                }
            }
            else {
                ball.count.value = ball.count.value!! - 1

                _balls.value!!.forEach { item ->
                    if(item.key.ordinal == ball.ballType.value!!.ordinal + 1) {
                        ball.update(true)
                    }
                    else {
                        ball.update(false)
                    }
                }
            }
        }

    }

    fun updateBalls(ballType: BallType = BallType.SNOOKER_RED){
        val ball = balls.value!![ballType]

        if(balls.value!![BallType.SNOOKER_RED]!!.count.value!! > 0) {
            if (ballType == BallType.SNOOKER_RED) {
                ball!!.count.value = ball.count.value?.minus(1)

                ball.update(false)

            }

        }
        else if(ballType != BallType.SNOOKER_RED){
            ball!!.count.value = ball.count.value?.minus(1)
        }

    }

    fun lockBalls(isLocked : Boolean = true){
        balls.value!!.forEach { ball ->
            ball.value.update(isLocked)
        }
    }


    fun addGlobalScorePlayer(){
        _globalScore.value = (_globalScore.value?: 0) + 1
    }

    fun clearGlobalScore(){
        _globalScore.value = 0
    }


    fun setScore(newScore: Int) {
        _score.value = newScore
    }

    fun isMax(addedScore: Int) : Boolean {
        return (_score.value ?: 0) + addedScore > maxScore
    }

    fun getNamePlayer(textPlayer : EditText, defaultName : String){

        if(textPlayer.text.isEmpty()){
            _name.value = defaultName
        }
        else{
            _name.value = textPlayer.text.toString()
        }
    }

    fun addHistoryFrame(){
        _historyFramePlayer.value?.add(score.value!!)
    }

    fun getPlayerModel(): PlayerModel {
        val playerModel = PlayerModel()
        playerModel.name = name.value!!
        playerModel.globalScore = globalScore.value!!
        historyFramePlayer.value!!.forEach { item ->
            playerModel.historyFramePlayer.add(item)
        }

        return playerModel
    }
}