package com.example.myfirstapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myfirstapp.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable.cancel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var player1: PlayerViewModel
//    lateinit var player2: PlayerViewModel


//    lateinit var progress: ProgressViewModel
    lateinit var snooker: SnookerViewModel

//    lateinit var scorePlayer1: TextView
//    lateinit var scorePlayer2: TextView
//
//    var isEnable: Boolean = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        player1 = PlayerViewModel()
//        player2 = PlayerViewModel()
//        progress = ProgressViewModel()

        snooker = SnookerViewModel(PlayerViewModel(), PlayerViewModel())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.snooker = snooker

//        binding.player1 = player1
//        binding.player2 = player2


//        binding.progress = progress

        binding.plus1 = 1
        binding.plus2 = 2
        binding.plus3 = 3
        binding.plus4 = 4
        binding.plus5 = 5
        binding.plus6 = 6
        binding.plus7 = 7


        binding.lifecycleOwner = this



        binding.cancelBtn.setOnClickListener {
            showCancelWindow()
        }

        binding.newGameBtn.setOnClickListener {
            showNewGameWindow()
        }
        snooker.frameScoreToString()
    }

    fun showCancelWindow() {

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.setMessage("Подтверждение действия")
            ?.setTitle("Отменить ход?")
            ?.setPositiveButton("ДА", { dialog, id ->
                dialog.dismiss()
                snooker.cancel()
            })
            ?.setNegativeButton("НЕТ", { dialog, id ->
                dialog.dismiss()
            })
            ?.create()?.show()

    }

    fun showNewGameWindow() {

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.setMessage("Подтверждение действия")
            ?.setTitle("Начать новую игру?")
            ?.setPositiveButton("ДА", { dialog, id ->
                dialog.dismiss()
                snooker.addGlobalScore()

            })
            ?.setNegativeButton("НЕТ", { dialog, id ->
                dialog.dismiss()
            })
            ?.create()?.show()

    }


//    fun move(player: PlayerViewModel, score: Int) {
//        snooker.move(player, score)
//
////        if(player == player1){
////            if(binding.plus1Player1Btn.isEnabled){
////                binding.plus1Player1Btn.isEnabled = false
////                binding.plus2Player1Btn.isEnabled = true
////                binding.plus3Player1Btn.isEnabled = true
////                binding.plus4Player1Btn.isEnabled = true
////                binding.plus5Player1Btn.isEnabled = true
////                binding.plus6Player1Btn.isEnabled = true
////                binding.plus7Player1Btn.isEnabled = true
////            }
////            else {
////                binding.plus1Player1Btn.isEnabled = true
////                binding.plus2Player1Btn.isEnabled = false
////                binding.plus3Player1Btn.isEnabled = false
////                binding.plus4Player1Btn.isEnabled = false
////                binding.plus5Player1Btn.isEnabled = false
////                binding.plus6Player1Btn.isEnabled = false
////                binding.plus7Player1Btn.isEnabled = false
////            }
////        }
////
////        if(player == player2){
////            if(binding.plus1Player2Btn.isEnabled){
////                binding.plus1Player2Btn.isEnabled = false
////                binding.plus2Player2Btn.isEnabled = true
////                binding.plus3Player2Btn.isEnabled = true
////                binding.plus4Player2Btn.isEnabled = true
////                binding.plus5Player2Btn.isEnabled = true
////                binding.plus6Player2Btn.isEnabled = true
////                binding.plus7Player2Btn.isEnabled = true
////            }
////            else {
////                binding.plus1Player2Btn.isEnabled = true
////                binding.plus2Player2Btn.isEnabled = false
////                binding.plus3Player2Btn.isEnabled = false
////                binding.plus4Player2Btn.isEnabled = false
////                binding.plus5Player2Btn.isEnabled = false
////                binding.plus6Player2Btn.isEnabled = false
////                binding.plus7Player2Btn.isEnabled = false
////            }
////        }
////
////        }
//
////        button.isEnabled = false
////        button.alpha = 0.8F
//    }
}

