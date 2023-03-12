package com.example.myfirstapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.BallType
import com.example.myfirstapp.models.PlayerModel


class PlayerViewModel : ViewModel() {
    private val maxScore = 147

    val name: LiveData<String> = MutableLiveData()
    fun changeName(newName: String) {
        (name as? MutableLiveData)?.value = newName
    }

    fun getName(defValue: String = "Player") : String {
        var result = (name as? MutableLiveData)?.value
        return if (result == null) {
            defValue
        } else {
            result!!
        }
    }

    val score: LiveData<Int> = MutableLiveData()
    fun addScore(addedScore: Int) {
        val newScore = (score as? MutableLiveData)?.value?.plus(addedScore)
        if (newScore != null) {
            if (newScore > maxScore) {
                (score as? MutableLiveData)?.value = maxScore
            }
            else {
                (score as? MutableLiveData)?.value = newScore
            }
        }
    }

    fun changeScore(newScore: Int) {
        if (newScore > maxScore) {
            (score as? MutableLiveData)?.value = maxScore
        }
        else {
            (score as? MutableLiveData)?.value = newScore
        }
    }

    fun getScore(defValue: Int = 0) : Int {
        val result = (score as? MutableLiveData)?.value
        return if (result == null) {
            defValue
        } else {
            result!!
        }
    }

    var globalScore: LiveData<Int> = MutableLiveData()
    fun addGlobalScore(addedGlobalScore: Int) {
        val newGlobalScore = (globalScore as? MutableLiveData)?.value?.plus(addedGlobalScore)
        (globalScore as? MutableLiveData)?.value = newGlobalScore
    }

    fun changeGlobalScore(newGlobalScore: Int) {
        (globalScore as? MutableLiveData)?.value = newGlobalScore
    }

    fun getGlobalScore(defValue: Int = 0) : Int {
        val result = (globalScore as? MutableLiveData)?.value
        return if (result == null) {
            defValue
        } else {
            result!!
        }
    }

    var framesPoints: LiveData<MutableList<Int>> = MutableLiveData()
    fun pushFramePoint(addedFramePoint: Int) {
        (framesPoints as? MutableLiveData)?.value?.add(addedFramePoint)
    }

    fun initFramePoints(framePoints: MutableList<Int> = mutableListOf()) {
        (framesPoints as? MutableLiveData)?.value?.clear()
        framePoints.forEach { item ->
            (framesPoints as? MutableLiveData)?.value?.add(item)
        }
    }

    fun getFramePoints(defValue: MutableList<Int> = mutableListOf()) : MutableList<Int> {
        val result = (framesPoints as? MutableLiveData)?.value
        return if (result == null) {
            defValue
        } else {
            result!!
        }
    }

    var balls: LiveData<MutableMap<BallType, SnookerBallViewModel>> = MutableLiveData()

    init {
        changeName("")
        (score as? MutableLiveData)?.value = 0
        (globalScore as? MutableLiveData)?.value = 0
        (framesPoints as? MutableLiveData)?.value = mutableListOf()

        (balls as? MutableLiveData)?.value = mutableMapOf()
        addBall(BallType.SNOOKER_RED, 15, 1)
        addBall(BallType.SNOOKER_YELLOW, 1, 2)
        addBall(BallType.SNOOKER_GREEN, 1, 3)
        addBall(BallType.SNOOKER_BROWN, 1, 4)
        addBall(BallType.SNOOKER_BLUE, 1, 5)
        addBall(BallType.SNOOKER_PURPLE, 1, 6)
        addBall(BallType.SNOOKER_BLACK, 1, 7)
    }

    private fun addBall(ballType: BallType, ballCount: Int, ballPoint: Int) {
        (balls as? MutableLiveData)?.value?.set(ballType,
            SnookerBallViewModel(ballPoint, ballCount, ballType)
        )
    }

//    fun updateBallsState(ballType: BallType, isCurrentPlayer: Boolean = true) {
//        val ball = _balls.value!![ballType]!!
//        if (isCurrentPlayer) {
//            if (_balls.value!![BallType.SNOOKER_RED]!!.count.value!! > 0) {
//                if (ball.ballType.value == BallType.SNOOKER_RED) {
//                    ball.count.value = ball.count.value!! - 1
//
//                    _balls.value!!.forEach { item ->
//                        item.value.update(true)
//                    }
//                    ball.update(false)
//                }
//                else {
//                    _balls.value!!.forEach { item ->
//                        item.value.update(false)
//                    }
//                    _balls.value!![BallType.SNOOKER_RED]!!.update(true)
//                }
//            }
//            else {
//                ball.count.value = ball.count.value!! - 1
//
//                _balls.value!!.forEach { item ->
//                    if(item.key.ordinal == ball.ballType.value!!.ordinal + 1) {
//                        ball.update(true)
//                    }
//                    else {
//                        ball.update(false)
//                    }
//                }
//            }
//        }
//
//    }

//    fun updateBalls(ballType: BallType = BallType.SNOOKER_RED){
//        val ball = balls.value!![ballType]
//
//        if(balls.value!![BallType.SNOOKER_RED]!!.count.value!! > 0) {
//            if (ballType == BallType.SNOOKER_RED) {
//                ball!!.count.value = ball.count.value?.minus(1)
//
//                ball.changeVisibility(false)
//
//            }
//
//        }
//        else if(ballType != BallType.SNOOKER_RED){
//            ball!!.count.value = ball.count.value?.minus(1)
//        }
//
//    }
//
//    fun lockBalls(isLocked : Boolean = true){
//        balls.value!!.forEach { ball ->
//            ball.value.changeVisibility(isLocked)
//        }
//    }
//
//
//    fun addGlobalScorePlayer(){
//        _globalScore.value = (_globalScore.value?: 0) + 1
//    }
//
//    fun clearGlobalScore(){
//        _globalScore.value = 0
//    }
//
//
//    fun setScore(newScore: Int) {
//        _score.value = newScore
//    }
//
//    fun isMax(addedScore: Int) : Boolean {
//        return (_score.value ?: 0) + addedScore > maxScore
//    }
//
//    fun getNamePlayer(textPlayer : EditText, defaultName : String){
//
//        if(textPlayer.text.isEmpty()){
//            changeName(defaultName)
////            _name.value = defaultName
//        }
//        else{
//            changeName(textPlayer.text.toString())
////            _name.value = textPlayer.text.toString()
//        }
//    }
//


    fun getPlayerModel(): PlayerModel {
        val playerModel = PlayerModel()

        playerModel.score = getScore(0)
        playerModel.name = getName("Player")
        playerModel.globalScore = getGlobalScore(0)

        val framePoints = getFramePoints(mutableListOf())
        framePoints.forEach { item ->
            playerModel.historyFramePlayer.add(item)
        }

        return playerModel
    }

    fun setPlayerModel(playerModel: PlayerModel){
        changeName(playerModel.name)
        changeScore(playerModel.score)
        changeGlobalScore(playerModel.globalScore)

        initFramePoints(playerModel.historyFramePlayer)
    }
}