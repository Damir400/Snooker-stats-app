package com.example.myfirstapp.activities


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
import androidx.lifecycle.get
import com.example.myfirstapp.models.DbConstants
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.models.CurrentGameModel
import com.example.myfirstapp.models.HistoryModel
import com.example.myfirstapp.viewModels.PlayerViewModel
import com.example.myfirstapp.viewModels.SnookerViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.history_list_item.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

//    val viewModel = ViewModelProvider(this).get<SnookerViewModel>()
//        .of(this)[SnookerViewModel::class.java]
    lateinit var snooker: SnookerViewModel
    lateinit var timer : CountDownTimer

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

        val currentPlay = readCurrentPlay()
        snooker = ViewModelProvider(this).get()

        if(currentPlay.first){
            val player1 = PlayerViewModel()
            player1.changeName("Player 1")
            val player2 = PlayerViewModel()
            player2.changeName("Player 20")

            currentPlay.second!!.player1?.let { player1.setPlayerModel(it) }
            currentPlay.second!!.player2?.let { player2.setPlayerModel(it) }

            snooker.player1.setPlayerModel(currentPlay.second!!.player1!!)
            snooker.player2.setPlayerModel(currentPlay.second!!.player2!!)

            currentTime = currentPlay.second!!.currentTimer

            if(currentTime <= 0){
                currentTime = timeOfTimerDefault
            }
            showTimer()
            overrideDefaultTime = false

        }
        else {
//            snooker = SnookerViewModel()
            snooker.player1.changeName("Player 1")
            snooker.player2.changeName("Player 2")
        }

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
//            snooker.player1.value!!.getNamePlayer(textUser1, "Игрок 1")
//            snooker.player2.value!!.getNamePlayer(textUser2, "Игрок 2")

//            snooker.player1.value!!.getNamePlayer(textUser1, "Игрок 1")
//            snooker.player2.value!!.getNamePlayer(textUser2,"Игрок 2")

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

        snooker.updateScoreTitle()
//        snooker.frameScoreToString()


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
    }

    override fun onPause() {
        super.onPause()
        saveCurrentPlayData()
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

//                snooker.player1.value!!.updateName(textUser1.text.toString())
//                snooker.player2.value!!.updateName(textUser2.text.toString())

//                snooker.player1.value!!.changeName(textUser1.text.toString())
//                snooker.player2.value!!.changeName(textUser2.text.toString())


                snooker.player1.changeName(textUser1.text.toString())// .value!!.changeName()
                snooker.player2.changeName(textUser2.text.toString())// .value!!.changeName()
//                snooker.player2.value!!.changeName(textUser2.text.toString())

                snooker.addHistoryPlayers()
                snooker.addGlobalScore()

//                timerOnLongClick()

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
//                timerOnLongClick()
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
//        binding.timerGame.setText("${timeToString(timerMin)}:${timeToString(timerSec)}")
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
        val gson = Gson()

        var historyModels = mutableListOf<HistoryModel>()

        var json = sharedPreference.getString(DbConstants.HISTORY_SAVE_KEY, "")

        if (json?.isNotEmpty() == true) {
            try {
                val historyModelsType = object : TypeToken<MutableList<HistoryModel>>() {}.type
                historyModels = Gson().fromJson<MutableList<HistoryModel>>(json, historyModelsType)
            }
            catch (exception: java.lang.Exception){
                println(exception.message)
            }
        }
//        val historyModel = HistoryModel()
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

    //--------------------------------------------------------------------------------------------------------------------------------------------------
    private fun saveCurrentPlayData(){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = sharedPreference.edit()
        val historyModel = snooker.getHistoryModel()

        var currentGameModel = CurrentGameModel()
        currentGameModel.currentTimer = currentTime
        currentGameModel.player1 = historyModel.player1
        currentGameModel.player2 = historyModel.player2

        val json = Gson().toJson(currentGameModel)
        prefsEditor.putString(DbConstants.CURRENTPLAY_SAVE_KEY, json)
        prefsEditor.commit()
    }

    private fun readCurrentPlay(): Pair<Boolean, CurrentGameModel?>{
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var currentGameModel: CurrentGameModel
        var json = sharedPreference.getString(DbConstants.CURRENTPLAY_SAVE_KEY, "")

        if (json?.isNotEmpty() == true) {
            try {
                val historyModelsType = object : TypeToken<CurrentGameModel>() {}.type
                currentGameModel = Gson().fromJson(json, historyModelsType)

                return Pair(true, currentGameModel)
            }
            catch (exception: java.lang.Exception){
                println(exception.message)
            }
        }

        return Pair(false, null)
    }
}

