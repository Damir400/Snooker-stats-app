package com.example.myfirstapp
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogCancelMove: DialogFragment()  {

    private lateinit var mainActivity: MainActivity


    fun setMainActivity(mainActivity: MainActivity){
        this.mainActivity = mainActivity
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Подтверждение действия")
                .setMessage("Отменить ход")
                .setPositiveButton("Да") {
                        dialog, id ->  dialog.dismiss()
                    mainActivity.cancelMove()
                }
                .setNegativeButton("Нет"){
                        dialog, id ->  dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}