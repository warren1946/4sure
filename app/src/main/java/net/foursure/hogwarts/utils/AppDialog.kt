package net.foursure.hogwarts.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.MainActivity
import net.foursure.hogwarts.SplashActivity
import net.foursure.hogwarts.models.Character


class AppDialog(){
    // Global variables
    private var _context: Context?=null

    constructor(context:Context):this(){
        this._context = context
    }

    // Create and show a generic alert dialog
    fun showBeforeExit(title:String, message:String){
        // Create and show the alert
        val dialogBuilder = AlertDialog.Builder(_context)
        // Set dialog alert message
        dialogBuilder.setMessage(message)
            // Don't allow the dialog to be canceled
            .setCancelable(false)
            // Set positive button text and action
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })

        // Create and display dialog
        val alert = dialogBuilder.create()
        // Set dialog alert title
        alert.setTitle(title)
        // show alert dialog
        alert.show()
    }
}