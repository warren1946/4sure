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

    // Create and show a generic alert dialog
    fun showMessage(context: Context, title:String, message:String){
        // Create and show the alert
        val dialogBuilder = AlertDialog.Builder(context)
        // Set dialog alert message
        dialogBuilder.setMessage(message)
            // Don't allow the dialog to be canceled
            .setCancelable(false)
            // Set positive button text and action
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })

        showDialog(dialogBuilder, title)
    }

    // Create and show a connection error alert dialog
    fun showConnectionErrorMessage(context: Context){
        // Create and show the alert
        val dialogBuilder = AlertDialog.Builder(context)
        // Set dialog alert message
        dialogBuilder.setMessage("Please make sure your device has internet connection before you continue.")
            // Don't allow the dialog to be canceled
            .setCancelable(false)
            // Set positive button text and action
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })

        showDialog(dialogBuilder, "Connection error");
    }

    fun showDialog(dialogBuilder:AlertDialog.Builder, title:String){
        // Create and display dialog
        val alert = dialogBuilder.create()
        // Set dialog alert title
        alert.setTitle(title)
        // show alert dialog
        alert.show()
    }
}