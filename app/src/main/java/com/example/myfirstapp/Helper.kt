package com.example.myfirstapp

import android.content.Context
import com.example.myfirstapp.models.CurrentGameModel
import com.example.myfirstapp.models.DbConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.SharedPreferences



// fun readCurrentGame(): Pair<Boolean, CurrentGameModel?>{
//    val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//    var currentPlayModel: CurrentGameModel
//
//    var json = sharedPreference.getString(DbConstants.CURRENTPLAY_SAVE_KEY, "")
//
//    if (json?.isNotEmpty() == true) {
//        try {
//            val historyModelsType = object : TypeToken<CurrentGameModel>() {}.type
//            currentPlayModel = Gson().fromJson(json, historyModelsType)
//            return Pair(true, currentPlayModel)
//        }
//        catch (exception: java.lang.Exception){
//            println(exception.message)
//        }
//    }
//
//    return Pair(false, null)
//}