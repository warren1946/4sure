package net.foursure.hogwarts.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import net.foursure.hogwarts.MainActivity
import net.foursure.hogwarts.SplashActivity


class AppDialog(){
    // Global variables
    private var _context: Context?=null
    private var _activity: Activity?=null

    constructor(context:Context, activity: Activity):this(){
        this._context = context
        this._activity = activity
    }

    // Create and show dialog
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

        // Display dialog
        displayDialog(dialogBuilder, title)
    }

    fun displayDialog(dialogBuilder:AlertDialog.Builder, title:String){
        // Create dialog box
        val alert = dialogBuilder.create()
        // Set dialog alert title
        alert.setTitle(title)
        // show alert dialog
        alert.show()
    }

}