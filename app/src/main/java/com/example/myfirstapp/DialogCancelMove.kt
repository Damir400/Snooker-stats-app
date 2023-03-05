//package com.example.myfirstapp
//import android.app.Dialog
//import android.os.Bundle
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.DialogFragment
//
//class DialogCancelMove: DialogFragment()  {
//
//    private lateinit var progressViewModel: ProgressViewModel
//
//
//    fun setMainActivity(progressViewModel: ProgressViewModel){
//        this.progressViewModel = progressViewModel
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle("Подтверждение действия")
//                .setMessage("Отменить ход")
//                .setPositiveButton("Да") {
//                        dialog, id ->  dialog.dismiss()
//                    progressViewModel.cancel()
//                }
//                .setNegativeButton("Нет"){
//                        dialog, id ->  dialog.dismiss()
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
//}