package net.foursure.hogwarts

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.utils.AppDialog

class StudentRegistryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_registry)

        // Display back button on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onBackPressed() {
        super.onBackPressed() // Activated when physical hardware back button is pressed
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Activated when toolbar/actionbar back button is pressed
        return true
    }

}
