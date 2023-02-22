package com.example.myfirstapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myfirstapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.NonCancellable.cancel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var player1: PlayerViewModel
//    lateinit var player2: PlayerViewModel


//    lateinit var progress: ProgressViewModel
    lateinit var snooker: SnookerViewModel

//    lateinit var bottomSheetFragment: BottomSheetFragment

//    lateinit var scorePlayer1: TextView
//    lateinit var scorePlayer2: TextView
//
//    var isEnable: Boolean = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        snooker = SnookerViewModel(PlayerViewModel(), PlayerViewModel())


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.snooker = snooker

        binding.plus1 = 1
        binding.plus2 = 2
        binding.plus3 = 3
        binding.plus4 = 4
        binding.plus5 = 5
        binding.plus6 = 6
        binding.plus7 = 7

        binding.lifecycleOwner = this




        val bottomSheetFragment = BottomSheetFragment(snooker)

        //------------------------------
        binding.history.setOnClickListener {
            snooker.player1.value!!.getNamePlayer(textUser1, "Игрок 1")
            snooker.player2.value!!.getNamePlayer(textUser2,"Игрок 2")

            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
//            bottomSheetFragment.updatePlayersName("player1 and player2")
        }
        //------------------------------



        binding.cancelBtn.setOnClickListener {
            showCancelWindow()
        }

        binding.newGameBtn.setOnClickListener {
            showNewGameWindow()
        }

        binding.newGameBtn.setOnLongClickListener{
            showNewTournamentWindow()
            true
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
            ?.setPositiveButton("ДА") { dialog, id ->
                dialog.dismiss()
                snooker.addHistoryPlayers()
                snooker.addGlobalScore()

            }
            ?.setNegativeButton("НЕТ", { dialog, id ->
                dialog.dismiss()
            })
            ?.create()?.show()
    }

    fun showNewTournamentWindow() {

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.setMessage("Подтверждение действия")
            ?.setTitle("Начать новую сессию?")
            ?.setPositiveButton("ДА", { dialog, id ->
                dialog.dismiss()
                snooker.newTournament()

            })
            ?.setNegativeButton("НЕТ", { dialog, id ->
                dialog.dismiss()
            })
            ?.create()?.show()
    }





//    fun history(){
//        val historyIntent = Intent(this, BottomSheetFragment::class.java)
//
//        val name1String = binding.textUser1.text.toString()
//        val name2String = binding.textUser2.text.toString()
//
//        val player1Score = snooker.player1.value!!.historyFramePlayer1.value!!.joinToString(separator = ",")
//        val player2Score = snooker.player2.value!!.historyFramePlayer2.value!!.joinToString(separator = ",")
//
//        historyIntent.putExtra(BottomSheetFragment.NAME1,name1String)
//        historyIntent.putExtra(BottomSheetFragment.NAME2,name2String)
//        historyIntent.putExtra(BottomSheetFragment.SCORE1, player1Score)
//        historyIntent.putExtra(BottomSheetFragment.SCORE2, player2Score)
//    }
}

