package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.myfirstapp.databinding.ActivityTableParamsBinding
import kotlinx.android.synthetic.main.activity_table_params.*

class TableParams : AppCompatActivity() {

    lateinit var binding: ActivityTableParamsBinding

    var btn12IsClick = false
    var btn11IsClick = false
    var btn10IsClick = false
    var btn9IsClick = false
    var btn8IsClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_params)

        binding = ActivityTableParamsBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.table12.setOnClickListener {
            if (!btn12IsClick){
                btn12IsClick = !btn12IsClick
                table12.setText(R.string.table12ft)
                table12.textSize = 15f
            }
            else {
                btn12IsClick = !btn12IsClick
                table12.setText("12 футов")
                table12.textSize = 28f
            }
        }

        binding.table11.setOnClickListener {
            if (!btn11IsClick){
                btn11IsClick = !btn11IsClick
                table11.setText(R.string.table11ft)
                table11.textSize = 15f
            }
            else {
                btn11IsClick = !btn11IsClick
                table11.setText("11 футов")
                table11.textSize = 28f
            }
        }

        binding.table10.setOnClickListener {
            if (!btn10IsClick){
                btn10IsClick = !btn10IsClick
                table10.setText(R.string.table10ft)
                table10.textSize = 15f
            }
            else {
                btn10IsClick = !btn10IsClick
                table10.setText("10 футов")
                table10.textSize = 28f
            }
        }

        binding.table9.setOnClickListener {
            if (!btn9IsClick){
                btn9IsClick = !btn9IsClick
                table9.setText(R.string.table9ft)
                table9.textSize = 15f
            }
            else {
                btn9IsClick = !btn9IsClick
                table9.setText("9 футов")
                table9.textSize = 28f
            }
        }

        binding.table8.setOnClickListener {
            if (!btn8IsClick){
                btn8IsClick = !btn8IsClick
                table8.setText(R.string.table8ft)
                table8.textSize = 15f
            }
            else {
                btn8IsClick = !btn8IsClick
                table8.setText("8 футов")
                table8.textSize = 28f
            }
        }
    }




}