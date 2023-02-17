package com.example.myfirstapp

import androidx.lifecycle.MutableLiveData

class SnookerBallViewModel constructor(ballPoints: Int, ballCount: Int, ballType: BallType) {
    private val _points = MutableLiveData<Int>()
    private val _isEnabled = MutableLiveData<Boolean>()
    private val _alpha = MutableLiveData<Float>()
    private val _count = MutableLiveData<Int>()
    private val _ballType = MutableLiveData<BallType>()

    var points: MutableLiveData<Int> = _points
    var isEnabled: MutableLiveData<Boolean> = _isEnabled
    var alpha: MutableLiveData<Float> = _alpha
    var count: MutableLiveData<Int> = _count
    var ballType: MutableLiveData<BallType> = _ballType

    init {
        _points.value = ballPoints
        _count.value = ballCount
        _ballType.value = ballType
        update(true)

    }

    fun update(isEnabled : Boolean){
        if(isEnabled){
            alpha.value = 1F
        }
        else{
            alpha.value = 0.5F
        }
        _isEnabled.value = isEnabled
    }

//    fun goal(isRedBallsEmpty: Boolean){
//        if (_ballType.value == BallType.SNOOKER_RED){
//            if (_count.value!! > 0){
//                _count.value = _count.value!! - 1
//                update(false)
//            }
//        }
//        else {
//            if (isRedBallsEmpty && _count.value!! > 0){
//                _count.value = _count.value!! - 1
//            }
//            update(false)
//        }}

}