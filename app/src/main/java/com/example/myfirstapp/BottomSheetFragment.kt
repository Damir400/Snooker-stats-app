package com.example.myfirstapp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import android.widget.TextView
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File.separator


class BottomSheetFragment(snookerViewModel: SnookerViewModel): BottomSheetDialogFragment() {

    lateinit var binding: BottomsheetFragmentBinding

    val snookerViewModel: SnookerViewModel

    init {
        this.snookerViewModel = snookerViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_fragment, container, false)

        binding.playersName1.text = snookerViewModel.player1.value!!.name.value
        binding.playersName2.text = snookerViewModel.player2.value!!.name.value

        binding.globalFinalScore.text = snookerViewModel.frameScore.value!!

        binding.historyFrame1.text = snookerViewModel.player1.value!!.historyFramePlayer.value?.joinToString(separator = "\n")
        binding.historyFrame2.text = snookerViewModel.player2.value!!.historyFramePlayer.value?.joinToString(separator = "\n")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}



