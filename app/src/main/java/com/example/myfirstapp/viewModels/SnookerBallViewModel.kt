package com.example.myfirstapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myfirstapp.BallType

class SnookerBallViewModel constructor(ballPoints: Int, ballCount: Int, ballType: BallType) {
    private val maxVisibility = 1f
    private val minVisibility = 0.5f
//    private val _points = MutableLiveData<Int>()
//    private val _isEnabled = MutableLiveData<Boolean>()
//    private val _alpha = MutableLiveData<Float>()
//    private val _count = MutableLiveData<Int>()
//    private val _ballType = MutableLiveData<BallType>()

//    var points: MutableLiveData<Int> = _points
//    var isEnabled: MutableLiveData<Boolean> = _isEnabled
//    var alpha: MutableLiveData<Float> = _alpha
//    var count: MutableLiveData<Int> = _count
//    var ballType: MutableLiveData<BallType> = _ballType

    val points: LiveData<Int> = MutableLiveData()
    val isEnabled: LiveData<Boolean> = MutableLiveData()//<Boolean> = _isEnabled
    val alpha: LiveData<Float> = MutableLiveData()//<Float> = _alpha
    val count: LiveData<Int> = MutableLiveData()//<Int> = _count
    val type: LiveData<BallType> = MutableLiveData()//<BallType> = _ballType


    init {
        (points as? MutableLiveData)?.value = ballPoints
        (count as? MutableLiveData)?.value = ballCount
        (type as? MutableLiveData)?.value = ballType

        (alpha as? MutableLiveData)?.value = maxVisibility
        (isEnabled as? MutableLiveData)?.value = true
//        _points.value = ballPoints
//        _count.value = ballCount
//        _ballType.value = ballType
//        update(true)
    }

    fun changeVisibility(isEnabled: Boolean){
        if (isEnabled){
            (alpha as? MutableLiveData)?.value = maxVisibility
//            alpha.value = 1F
        }
        else {
            (alpha as? MutableLiveData)?.value = minVisibility
//            alpha.value = 0.5F
        }
//        _isEnabled.value = isEnabled
        (this.isEnabled as? MutableLiveData)?.value = false
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