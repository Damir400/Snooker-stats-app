package com.example.myfirstapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myfirstapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.NonCancellable.cancel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var player1: PlayerViewModel
//    lateinit var player2: PlayerViewModel


//    lateinit var progress: ProgressViewModel
    lateinit var snooker: SnookerViewModel

    lateinit var timer : CountDownTimer
    var timerMin = 0
    var timerSec = 0
    var installTime = ""
    var counterToochingPlayBtn = 0

    var mMediaPlayer: MediaPlayer? = null

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

        binding.history.setOnClickListener {
            snooker.player1.value!!.getNamePlayer(textUser1, "Игрок 1")
            snooker.player2.value!!.getNamePlayer(textUser2,"Игрок 2")

            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }


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

        binding.switchThemes.setOnClickListener{
            changeImgBall()
        }

        binding.playTimer.setOnClickListener{
            counterToochingPlayBtn ++
            if (counterToochingPlayBtn % 2 != 0) {
                playTimer.setImageDrawable(
                    getResources().getDrawable(
                        R.drawable.stop,
                        getApplicationContext().getTheme()
                    )
                )
                startTimer()
                timer.start()
            }
            else {
                playTimer.setImageDrawable(
                    getResources().getDrawable(
                        R.drawable.play,
                        getApplicationContext().getTheme()
                    )
                )
                timer.cancel()
            }
        }

//        binding.playTimer.setOnLongClickListener{
//            startTimer()
//        }


        snooker.frameScoreToString()


    }

    fun showCancelWindow() {

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.setTitle("Отменить ход?")
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
        builder.setTitle("Начать новую игру?")
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
        builder.setTitle("Начать новую сессию?")
            ?.setPositiveButton("ДА", { dialog, id ->
                dialog.dismiss()
                snooker.newTournament()

            })
            ?.setNegativeButton("НЕТ", { dialog, id ->
                dialog.dismiss()
            })
            ?.create()?.show()
    }

    fun changeImgBall(){
        if (switchThemes.isChecked){
            plus1Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball1, getApplicationContext().getTheme()))
            plus2Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball2, getApplicationContext().getTheme()))
            plus3Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball3, getApplicationContext().getTheme()))
            plus4Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball4, getApplicationContext().getTheme()))
            plus5Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball5, getApplicationContext().getTheme()))
            plus6Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball6, getApplicationContext().getTheme()))
            plus7Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball7, getApplicationContext().getTheme()))

            plus1Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball1, getApplicationContext().getTheme()))
            plus2Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball2, getApplicationContext().getTheme()))
            plus3Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball3, getApplicationContext().getTheme()))
            plus4Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball4, getApplicationContext().getTheme()))
            plus5Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball5, getApplicationContext().getTheme()))
            plus6Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball6, getApplicationContext().getTheme()))
            plus7Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.a_ball7, getApplicationContext().getTheme()))
        }
        else {
            plus1Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball1, getApplicationContext().getTheme()))
            plus2Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball2, getApplicationContext().getTheme()))
            plus3Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball3, getApplicationContext().getTheme()))
            plus4Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball4, getApplicationContext().getTheme()))
            plus5Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball5, getApplicationContext().getTheme()))
            plus6Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball6, getApplicationContext().getTheme()))
            plus7Player1Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball7, getApplicationContext().getTheme()))

            plus1Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball1, getApplicationContext().getTheme()))
            plus2Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball2, getApplicationContext().getTheme()))
            plus3Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball3, getApplicationContext().getTheme()))
            plus4Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball4, getApplicationContext().getTheme()))
            plus5Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball5, getApplicationContext().getTheme()))
            plus6Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball6, getApplicationContext().getTheme()))
            plus7Player2Btn.setImageDrawable(getResources().getDrawable(R.drawable.ball7, getApplicationContext().getTheme()))        }
    }


    fun startTimer(){
        timer = object : CountDownTimer(convertTime(timer_game, 600000), 1000){
            override fun onTick(p0: Long) {
                showTimer()
            }

            override fun onFinish() {
                playSound()
                timer_game.setText(installTime)
                playTimer.setImageDrawable(
                    getResources().getDrawable(
                        R.drawable.play,
                        getApplicationContext().getTheme()
                    )
                )
            }

        }
    }

    fun convertTime(time : EditText, defaultTime : Long): Long {

        var convertTime : Long

        if (time.text.isEmpty()){

            timerMin = (defaultTime / 60000).toInt()
            timerSec = ((defaultTime - timerMin * 60000) / 1000).toInt()
            installTime = "${timerMin}:${timerSec}"
            return defaultTime
        } else{
            var currentTime = time.text.trim().splitToSequence(':').toList()
            convertTime = (currentTime.get(0).toInt() * 60000 + currentTime.get(1).toInt() * 1000).toLong()

            timerMin = currentTime.get(0).toInt()
            timerSec = currentTime.get(1).toInt()
            installTime = time.text.toString()
            return convertTime
        }
    }

    fun showTimer(){
        if(timerMin < 10){
            if (timerSec > 0){
                if (timerSec < 11){
                    timerSec -= 1
                    timer_game.setText("0${timerMin}:0${timerSec}")
                    return
                }
                timerSec -= 1
                timer_game.setText("0${timerMin}:${timerSec}")
                return
            }
            else if (timerMin > 0 && timerSec == 0){
                timerMin -= 1
                timerSec = 59
                timer_game.setText("0${timerMin}:${timerSec}")
                return
            }
            else if(timerMin == 0 && timerSec == 0){
                timer_game.setText("00:00")
                return
            }
        }

        if (timerSec > 0){
            if (timerSec < 11){
                timerSec -= 1
                timer_game.setText("${timerMin}:0${timerSec}")
                return
            }
            timerSec -= 1
            timer_game.setText("${timerMin}:${timerSec}")
            return
        }
    }

    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.budilnik)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

}

