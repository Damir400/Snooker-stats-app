package com.example.myfirstapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapp.databinding.ActivityHistoryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryBinding
    lateinit var mPrefs: SharedPreferences
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        binding.lifecycleOwner = this
//        mPrefs =

        initRcView()
    }

    private fun initRcView() = with(binding){
        adapter = HistoryAdapter()
        rcView.layoutManager = LinearLayoutManager(this@HistoryActivity)
        rcView.adapter = adapter

        adapter.submitList(readHistory().toList().reversed())
    }


    private fun readHistory(): MutableList<HistoryModel>{
//        mPrefs!!.edit()
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val gson = Gson()
        var historyModels = mutableListOf<HistoryModel>()

        var json = sharedPreference.getString(DbConstants.HISTORY_SAVE_KEY, "")

//
        if (json?.isNotEmpty() == true) {
            try {
                val historyModelsType = object : TypeToken<MutableList<HistoryModel>>() {}.type
                historyModels = Gson().fromJson(json, historyModelsType)
            }
            catch (exception: java.lang.Exception){
                println(exception.message)
            }
        }

        return historyModels
    }

}