package net.foursure.hogwarts

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.dao.HogwartsAPI
import net.foursure.hogwarts.models.House
import net.foursure.hogwarts.models.Member
import net.foursure.hogwarts.utils.AppDialog
import net.foursure.hogwarts.utils.Constants
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    //Global variables
    val LOG:String = "MainActivity"
    private var requestQueue: RequestQueue? = null

    // Dashboard cards
    private var house_card:CardView?=null
    private var reg_card:CardView?=null
    private var spell_card:CardView?=null

    // counters
    private var house_count:TextView?=null
    private var reg_count:TextView?=null
    private var spell_count:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set click listener to the house cardview
        house_card = findViewById<CardView>(R.id.house_card)
        house_card?.setOnClickListener(clicked)
        // Set click listener to the student registry cardview
        reg_card = findViewById<CardView>(R.id.reg_card)
        reg_card?.setOnClickListener(clicked)
        // Set click listener to the spells cardview
        spell_card = findViewById<CardView>(R.id.spell_card)
        spell_card?.setOnClickListener(clicked)

        // initialize Counter textviews
        house_count = findViewById<TextView>(R.id.house_count)
        reg_count = findViewById<TextView>(R.id.reg_count)
        spell_count = findViewById<TextView>(R.id.spell_count)

        // Count number of houses, student registry and spells from api and assign to textview
        requestQueue = Volley.newRequestQueue(this)
        getHouseList()
        getStudentRegistryList()
        getSpellList()

    }

    // Navigate to the clicked cardview's home activity
    private val clicked: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) { // Check which cardview was clicked.
            R.id.house_card -> gotoHouseScreen()
            R.id.reg_card -> gotoStudentRegistry()
            R.id.spell_card -> gotoSpells()
        }
    }

    private fun gotoHouseScreen() { // Navigate to house screen
        val intent = Intent(this, HouseActivity::class.java)
        startActivity(intent)
    }

    private fun gotoStudentRegistry() { // Navigate to student registry screen
        val intent = Intent(this, StudentRegistryActivity::class.java)
        startActivity(intent)
    }

    private fun gotoSpells() { // Navigate to spells screen
        val intent = Intent(this, SpellsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                showBeforeExit() // Show dialog before exiting the app
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getHouseList(){
        // Url to make a request
        val url = Constants.CONS_HOUSE_LIST + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
        response ->try {
            // Process api call response and assign to textview
            house_count?.setText(response.length().toString())
        } catch (e: JSONException) {
            Log.e(LOG, e.message)
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    fun getStudentRegistryList(){
        // Url to make a request
        val url = Constants.CONS_CHARACTER_LIST + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response and assign to textview
            reg_count?.setText(response.length().toString())
        } catch (e: JSONException) {
            Log.e(LOG, e.message)
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    fun getSpellList(){
        // Url to make a request
        val url = Constants.CONS_SPELL_LIST + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response and assign to textview
            spell_count?.setText(response.length().toString())
        } catch (e: JSONException) {
            Log.e(LOG, e.message)
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    // Create and show dialog
    fun showBeforeExit(){
        // Create and show the alert
        val dialogBuilder = AlertDialog.Builder(this)
        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure you want to exit the app?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> clearStackAndExit()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Exit")
        // show alert dialog
        alert.show()
    }

    fun clearStackAndExit(){
        finishAffinity()
        System.exit(0)
    }
}
