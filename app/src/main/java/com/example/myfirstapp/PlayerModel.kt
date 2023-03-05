package com.example.myfirstapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.Date

public class PlayerModel() {
   public var name : String = ""
   public var globalScore: Int = 0
   public var historyFramePlayer: MutableList<Int> = mutableListOf()

}