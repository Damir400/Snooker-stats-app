package com.example.myfirstapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myfirstapp.databinding.MenuMainBinding


class MainMenu: AppCompatActivity() {

    lateinit var binding: MenuMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.menu_main)

        binding.lifecycleOwner = this


        binding.newGame.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

        binding.paramsTable.setOnClickListener {
            val intent = Intent(this, TableParams::class.java);
            startActivity(intent);
        }
    }

}