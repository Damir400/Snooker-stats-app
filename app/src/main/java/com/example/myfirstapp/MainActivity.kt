package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var player1 = Player(0)
    var player2 = Player(0)

    val progress = mutableListOf<Triple<Player, Int, TextView>>()

    lateinit var scorePlayer1 : TextView
    lateinit var scorePlayer2 : TextView

    lateinit var buttonPlayer1 : Button
    lateinit var buttonPlayer2 : Button
    lateinit var buttonPlayer3 : Button
    lateinit var buttonPlayer4 : Button
    lateinit var buttonPlayer5 : Button
    lateinit var buttonPlayer6 : Button
    lateinit var buttonPlayer7 : Button
    lateinit var buttonPlayer8 : Button
    lateinit var buttonPlayer9 : Button
    lateinit var buttonPlayer10 : Button
    lateinit var buttonPlayer11 : Button
    lateinit var buttonPlayer12 : Button
    lateinit var buttonPlayer13 : Button
    lateinit var buttonPlayer14 : Button
    lateinit var buttonPlayer15 : Button
    lateinit var buttonPlayer16 : Button
    lateinit var buttonPlayer17 : Button
    lateinit var buttonPlayer18 : Button
    lateinit var buttonPlayer19 : Button
    lateinit var buttonPlayer20 : Button
    lateinit var buttonPlayer21 : Button
    lateinit var buttonPlayer22 : Button
    lateinit var buttonPlayer23 : Button
    lateinit var buttonPlayer24 : Button

    fun addScore(score: Int, player: Player, scoreView: TextView){
        if(player.score + score <= 147){
            player.score += score
            scoreView.text = "${player.score}"
            progress.add(Triple(player,-score, scoreView))
        }
        else {
            player.score = 147
            scoreView.text = "${player.score}"
        }
    }

    fun cancel(){
        if(!progress.isEmpty()){
            progress.last().first.score += progress.last().second
            progress.last().third.text = "${progress.last().first.score}"
            progress.removeLast()
        }
    }

    fun reset(player: Player, scoreView: TextView){
        player.score = 0
        scoreView.text = "${player.score}"
        progress.clear()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scorePlayer1 = findViewById<TextView>(R.id.textScore1)
        scorePlayer2 = findViewById<TextView>(R.id.textScore2)
        buttonPlayer1 = findViewById<Button>(R.id.button1)
        buttonPlayer2 = findViewById<Button>(R.id.button2)
        buttonPlayer3 = findViewById<Button>(R.id.button3)
        buttonPlayer4 = findViewById<Button>(R.id.button4)
        buttonPlayer5 = findViewById<Button>(R.id.button5)
        buttonPlayer6 = findViewById<Button>(R.id.button6)
        buttonPlayer7 = findViewById<Button>(R.id.button7)
        buttonPlayer8 = findViewById<Button>(R.id.button8)
        buttonPlayer9 = findViewById<Button>(R.id.button9)
        buttonPlayer10 = findViewById<Button>(R.id.button10)
        buttonPlayer11 = findViewById<Button>(R.id.button11)
        buttonPlayer12 = findViewById<Button>(R.id.button12)
        buttonPlayer13 = findViewById<Button>(R.id.button13)
        buttonPlayer14 = findViewById<Button>(R.id.button14)
        buttonPlayer15 = findViewById<Button>(R.id.button15)
        buttonPlayer16 = findViewById<Button>(R.id.button16)
        buttonPlayer17 = findViewById<Button>(R.id.button17)
        buttonPlayer18 = findViewById<Button>(R.id.button18)
        buttonPlayer19 = findViewById<Button>(R.id.button19)
        buttonPlayer20 = findViewById<Button>(R.id.button20)
        buttonPlayer21 = findViewById<Button>(R.id.button21)
        buttonPlayer22 = findViewById<Button>(R.id.button22)
        buttonPlayer23 = findViewById<Button>(R.id.button23)
        buttonPlayer24 = findViewById<Button>(R.id.button24)

        buttonPlayer1.setOnClickListener{
            addScore(1,player1,scorePlayer1)
        }

        buttonPlayer2.setOnClickListener{
            addScore(2, player1,scorePlayer1)
        }

        buttonPlayer3.setOnClickListener{
            addScore(3, player1,scorePlayer1)
        }

        buttonPlayer4.setOnClickListener{
            addScore(4, player1,scorePlayer1)
        }

        buttonPlayer5.setOnClickListener{
            addScore(5, player1,scorePlayer1)
        }

        buttonPlayer6.setOnClickListener{
            addScore(6, player1,scorePlayer1)
        }

        buttonPlayer7.setOnClickListener{
            addScore(7, player1,scorePlayer1)
        }

        buttonPlayer8.setOnClickListener{
            addScore(1, player2,scorePlayer2)

        }

        buttonPlayer9.setOnClickListener{
            addScore(2, player2,scorePlayer2)
        }

        buttonPlayer10.setOnClickListener{
            addScore(3, player2,scorePlayer2)
        }

        buttonPlayer11.setOnClickListener{
            addScore(4, player2,scorePlayer2)
        }

        buttonPlayer12.setOnClickListener{
            addScore(5, player2,scorePlayer2)
        }

        buttonPlayer13.setOnClickListener{
            addScore(6, player2,scorePlayer2)
        }

        buttonPlayer14.setOnClickListener{
            addScore(7, player2,scorePlayer2)
        }
// вычитание
        buttonPlayer15.setOnClickListener{
            addScore(4, player2,scorePlayer2)
        }

        buttonPlayer16.setOnClickListener{
            addScore(5, player2,scorePlayer2)
        }

        buttonPlayer17.setOnClickListener{
            addScore(4, player1,scorePlayer1)
        }

        buttonPlayer18.setOnClickListener{
            addScore(5, player1,scorePlayer1)

        }

        buttonPlayer19.setOnClickListener{
            addScore(6, player2,scorePlayer2)
        }

        buttonPlayer20.setOnClickListener{
            addScore(7, player2,scorePlayer2)
        }

        buttonPlayer21.setOnClickListener{
            addScore(6, player1,scorePlayer1)
        }

        buttonPlayer22.setOnClickListener{
            addScore(7, player1,scorePlayer1)
        }

        //Отмена хода
        buttonPlayer23.setOnClickListener{
            cancel()
        }

        //сброс
        buttonPlayer24.setOnClickListener{
            reset(player1,scorePlayer1)
            reset(player2,scorePlayer2)
        }

    }
}