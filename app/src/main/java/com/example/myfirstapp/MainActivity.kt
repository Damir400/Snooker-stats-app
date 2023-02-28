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
    var timeOfTimer = 60 * 15
    var timeOfTimerDefault = 60 * 15
    var timerIsPaused = true

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

        binding.playTimer.setOnClickListener { timerOnClick() }

        binding.playTimer.setOnLongClickListener {
            timerOnLongClick()
            return@setOnLongClickListener true
        }
        snooker.frameScoreToString()
    }

    fun timerOnLongClick() {
        timer.cancel()
        timer.onFinish()
    }

    fun timerOnClick() {
        timerIsPaused = !timerIsPaused

        if(timerIsPaused) {
            playTimer.setImageDrawable(
                resources.getDrawable(
                    R.drawable.play,
                    applicationContext.theme
                )
            )
            timer.cancel()
        }
        else {
            playTimer.setImageDrawable(
                getResources().getDrawable(
                    R.drawable.stop,
                    getApplicationContext().getTheme()
                )

            )

            startTimer2()
//            timer.start()
        }
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

    fun startTimer2(){
        timer = object : CountDownTimer(convertTime2(timer_game), 1000){
            override fun onTick(p0: Long) {
                if (timeOfTimer > 0) {
                    timeOfTimer--
                }

                showTimer2()
            }

            override fun onFinish() {
                playSound()
                timerIsPaused = true
                timeOfTimer = timeOfTimerDefault
                showTimer2()
                playTimer.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.play,
                        applicationContext.theme
                    )
                )
            }
        }

        timer.start()
    }

    fun timeToString(anytime: Int) : String {
        return if(anytime >= 10) {
            "$anytime"
        } else {
            "0$anytime"
        }
    }

    fun showTimer2() {
        val timerMin = timeOfTimer / 60
        val timerSec = timeOfTimer % 60

        timer_game.setText("${timeToString(timerMin)}:${timeToString(timerSec)}")
    }

    fun convertTime2(timeStr: EditText) : Long {

        if (!timeStr.text.isEmpty()) {
            var times = timeStr.text.trim().splitToSequence(':').toList()
            timeOfTimerDefault = times[0].toInt() * 60 + times[1].toInt()

        }
        timeOfTimer = timeOfTimerDefault
        showTimer2()
        return (timeOfTimer * 1000).toLong()
    }

    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.budilnik)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

}

