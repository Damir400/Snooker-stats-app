package com.example.myfirstapp;
import kotlin.reflect.KProperty

public class Player(_score: Int) {
    //var score: Int by PlayerScoreDelegate(_score)
    var score: Int = _score

    val progress = mutableListOf<Int>()
}
//public class PlayerScoreDelegate(var score: Int) {
//    operator fun setValue(thisRef: Player, property: KProperty<*>, addScore: Int) {
//        score += addScore
//    }
//
//    operator fun getValue(thisRef: Player, property: KProperty<*>): Int {
//        return score
//    }
//}
