package com.example.myfirstapp


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.history_list_item.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var snooker: SnookerViewModel


    lateinit var timer : CountDownTimer
//    var timeOfTimer = 60 * 25
    var timeOfTimerDefault = 60 * 25
    var timerIsPaused = true
    var currentTime = 0
    var overrideDefaultTime = true
    var mMediaPlayer: MediaPlayer? = null

    var mPrefs: SharedPreferences? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        snooker = SnookerViewModel(PlayerViewModel("Player 1"), PlayerViewModel("Player 2"))
//        var snooker by lazy { ViewModelProvider(this).get(SnookerViewModel(PlayerViewModel("Player 1"), PlayerViewModel("Player 2"))::class.java) }

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

        binding.timerGame.doAfterTextChanged {
            if(timerIsPaused && timer_game.text.toString().length == 5){
                convertTime(timer_game)
                overrideDefaultTime = true
            }
        }

        snooker.frameScoreToString()


        mPrefs = getPreferences(MODE_PRIVATE)

        binding.textUser1.setOnClickListener { _ -> textUser1.isCursorVisible = true }
        binding.textUser2.setOnClickListener { _ -> textUser2.isCursorVisible = true }
        binding.timerGame.setOnClickListener { _ -> timer_game.isCursorVisible = true }

        binding.textUser1.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                return onEditTextViewEditorAction(v, actionId, event)
            }
        })

        binding.textUser2.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                return onEditTextViewEditorAction(v, actionId, event)
            }
        })

        binding.timerGame.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                return onEditTextViewEditorAction(v, actionId, event)
            }
        })

//        binding.textUser1.setOnFocusChangeListener {view, b -> textUser1.isCursorVisible = b}

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putString("KEY1",snooker.player1.value!!.name.value!!.toString())
            putString("KEY2",textUser2.text.toString())
//            putString("KEY3",.text.toString())
//            putString("KEY4",textUser1.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        textUser1.setText("KEY1")
    }



    fun onEditTextViewEditorAction(currentView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
            actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_NEXT ||
            event != null &&
            event.getAction() == KeyEvent.ACTION_DOWN &&
            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (event == null || !event.isShiftPressed()) {
                // the user is done typing.
                currentView?.isCursorVisible = false
                return false; // consume.
            }
        }
        currentView?.isCursorVisible = true
        return true; // pass on to other listeners.
    }

    private fun timerOnLongClick() {
        timer.cancel()
        timer.onFinish()
    }

    private fun timerOnClick() {
        timerIsPaused = !timerIsPaused

        if(timerIsPaused) {
            playTimer.setImageDrawable(
                resources.getDrawable(
                    R.drawable.play,
                    applicationContext.theme
                )
            )
            timer_game.isEnabled = true
            timer.cancel()
//            currentTime = timeOfTimer
        }
        else {
            playTimer.setImageDrawable(
                resources.getDrawable(
                    R.drawable.stop,
                    applicationContext.theme
                )

            )
            timer_game.isEnabled = false
            startTimer(overrideDefaultTime)
            overrideDefaultTime = false
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

                snooker.player1.value!!.updateName(textUser1.text.toString())
                snooker.player2.value!!.updateName(textUser2.text.toString())

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
                checkZeroScore()
                saveData()
                snooker.newTournament()

            })
            ?.setNegativeButton("НЕТ", { dialog, id ->
                dialog.dismiss()
            })
            ?.create()?.show()
    }

    fun changeImgBall(){
        if (switchThemes.isChecked){
            plus1Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball1, applicationContext.theme))
            plus2Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball2, applicationContext.theme))
            plus3Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball3, applicationContext.theme))
            plus4Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball4, applicationContext.theme))
            plus5Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball5, applicationContext.theme))
            plus6Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball6, applicationContext.theme))
            plus7Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball7, applicationContext.theme))

            plus1Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball1, applicationContext.theme))
            plus2Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball2, applicationContext.theme))
            plus3Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball3, applicationContext.theme))
            plus4Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball4, applicationContext.theme))
            plus5Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball5, applicationContext.theme))
            plus6Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball6, applicationContext.theme))
            plus7Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.a_ball7, applicationContext.theme))
        }
        else {
            plus1Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball1, applicationContext.theme))
            plus2Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball2, applicationContext.theme))
            plus3Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball3, applicationContext.theme))
            plus4Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball4, applicationContext.theme))
            plus5Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball5, applicationContext.theme))
            plus6Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball6, applicationContext.theme))
            plus7Player1Btn.setImageDrawable(resources.getDrawable(R.drawable.ball7, applicationContext.theme))

            plus1Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball1, applicationContext.theme))
            plus2Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball2, applicationContext.theme))
            plus3Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball3, applicationContext.theme))
            plus4Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball4, applicationContext.theme))
            plus5Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball5, applicationContext.theme))
            plus6Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball6, applicationContext.theme))
            plus7Player2Btn.setImageDrawable(resources.getDrawable(R.drawable.ball7, applicationContext.theme))        }
    }

    private fun startTimer(newTimer: Boolean = true) {

        var newTime: Long
        if (newTimer) {
            newTime = (timeOfTimerDefault * 1000).toLong()
            currentTime = timeOfTimerDefault
        } else {
            newTime = (currentTime * 1000).toLong()
        }


        timer = object : CountDownTimer(newTime, 1000){
            override fun onTick(p0: Long) {
                if (currentTime > 0) {
                    currentTime--
                }

                showTimer()
            }

            override fun onFinish() {
                if(timer_game.text.toString() == "00:01"){
                    playSound()
                }

                timerIsPaused = true
                currentTime = timeOfTimerDefault
                showTimer()

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

    private fun timeToString(anytime: Int) : String {
        return if(anytime >= 10) {
            "$anytime"
        } else {
            "0$anytime"
        }
    }

    fun showTimer() {
        val timerMin = currentTime / 60
        val timerSec = currentTime % 60

        timer_game.setText("${timeToString(timerMin)}:${timeToString(timerSec)}")
    }

    private fun convertTime(timeStr: EditText)  {
        if (timeStr.text.isNotEmpty()) {
            var times = timeStr.text.trim().splitToSequence(':').toList()
            timeOfTimerDefault = times[0].toInt() * 60 + times[1].toInt()
        }
        overrideDefaultTime = true
    }

    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.budilnik)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun saveData(){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = sharedPreference.edit()
//        val prefsEditor: SharedPreferences.Editor = mPrefs!!.edit()
        val gson = Gson()

        var historyModels = mutableListOf<HistoryModel>()

        var json = sharedPreference.getString(DbConstants.HISTORY_SAVE_KEY, "")

        if (json?.isNotEmpty() == true) {
            try {
                val historyModelsType = object : TypeToken<MutableList<HistoryModel>>() {}.type
                historyModels = Gson().fromJson<MutableList<HistoryModel>>(json, historyModelsType)
//             historyModel = gson.fromJson(json, MutableList<HistoryModel>::class.java)
            }
            catch (exception: java.lang.Exception){
                println(exception.message)
            }
        }


        val historyModel = HistoryModel()
        historyModels.add(snooker.getHistoryModel())

        json = gson.toJson(historyModels)
        prefsEditor.putString(DbConstants.HISTORY_SAVE_KEY, json)
        prefsEditor.commit()
    }

    fun checkZeroScore(){
        if(textScore1.text.toString().toInt() != 0 && textScore2.text.toString().toInt() != 0){
            snooker.addHistoryPlayers()
            snooker.addGlobalScore()
        }
    }


}

