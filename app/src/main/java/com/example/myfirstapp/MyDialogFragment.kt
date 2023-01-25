package com.example.myfirstapp
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogNewGame : DialogFragment() {

    private lateinit var mainActivity: MainActivity


    fun setMainActivity(mainActivity: MainActivity){
        this.mainActivity = mainActivity
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Подтверждение действия")
                .setMessage("Вы уверены, что хотите начать новую игру?")
                .setPositiveButton("Да") {
                        dialog, id ->  dialog.dismiss()
                    mainActivity.resetGame()
                    mainActivity.addGlobalScore()
                }
                .setNegativeButton("Нет"){
                        dialog, id ->  dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}