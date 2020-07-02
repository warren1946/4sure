package net.foursure.hogwarts

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.adapter.SpellsRvAdapter
import net.foursure.hogwarts.models.Spell
import net.foursure.hogwarts.utils.AppDialog
import net.foursure.hogwarts.utils.Constants
import org.json.JSONException

class SpellsActivity : AppCompatActivity() {
    //Global variables
    val LOG:String = "SpellsActivity"
    private var requestQueue: RequestQueue? = null
    // Recycler view and shimmer layout
    private var recyclerView:RecyclerView?=null
    private var shimmerLayout:ShimmerFrameLayout?=null
    private var rvAdapter:SpellsRvAdapter?=null
    private var spellList: ArrayList<Spell>?=null

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spells)

        // Display back button on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Initial recycler view and shimmer layout
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        shimmerLayout = findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        shimmerLayout?.startLayoutAnimation() // Start animation

        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager

        // Initialize adapter and pass the values to RvAdapter
        spellList = ArrayList<Spell>()
        rvAdapter = SpellsRvAdapter(spellList!!, this)
        // set the recyclerView to the adapter
        recyclerView?.adapter = rvAdapter;

        // Request spell list from the api textview
        requestQueue = Volley.newRequestQueue(this)
        getSpellList()
    }

    fun getSpellList() {
        // Url to make a request
        val url = Constants.CONS_SPELL_LIST + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response and add data to the list
            for (i in 0 until response.length()) {
                val jsonSpell = response.getJSONObject(i)

                val spell: Spell = Spell()
                spell._id = jsonSpell.getString("_id")
                spell.spell = jsonSpell.getString("spell")
                spell.type = jsonSpell.getString("type")
                spell.effect = jsonSpell.getString("effect")

                // Add processed spell to the list
                spellList?.add(spell)
            }

            // Update adapter with new data and close shimmer
            rvAdapter?.notifyDataSetChanged()
            shimmerLayout?.stopShimmer()
            shimmerLayout?.visibility = View.GONE

        } catch (e: JSONException) {
            Log.e(LOG, e.message)
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            shimmerLayout?.stopShimmer()
            shimmerLayout?.visibility = View.GONE
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    override fun onBackPressed() {
        super.onBackPressed() // Activated when physical hardware back button is pressed
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Activated when toolbar/actionbar back button is pressed
        return true
    }
}
