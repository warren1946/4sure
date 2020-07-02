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
import net.foursure.hogwarts.adapter.MemberRvAdapter
import net.foursure.hogwarts.models.Character
import net.foursure.hogwarts.models.Member
import net.foursure.hogwarts.utils.AppDialog
import net.foursure.hogwarts.utils.Constants
import net.foursure.hogwarts.utils.NetworkManager
import org.json.JSONException

class StudentRegistryActivity : AppCompatActivity() {
    // Global variables
    val LOG:String = "StudentRegistryActivity"
    private var requestQueue: RequestQueue? = null

    // Recycler view and shimmer layout
    private var recyclerView: RecyclerView?=null
    private var shimmerLayout: ShimmerFrameLayout?=null
    private var rvAdapter: MemberRvAdapter?=null
    private var memberList: ArrayList<Member>?=null

    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_registry)

        // Display back button on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Initialize the request queue
        requestQueue = Volley.newRequestQueue(this)

        //Initial recycler view and shimmer layout
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        shimmerLayout = findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        shimmerLayout?.startLayoutAnimation() // Start animation

        // initialize layout manager
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager

        // Initialize adapter and pass the values to RvAdapter
        memberList = ArrayList<Member>()
        rvAdapter = MemberRvAdapter(memberList!!, this)
        // set the recyclerView to the adapter
        recyclerView?.adapter = rvAdapter;

        // Check for internet connection before making APi calls
        if(NetworkManager().isOnline(this)) {
            // Populate data
            getCharacterList()
        }else{
            AppDialog().showConnectionErrorMessage(this)
        }

    }

    fun getCharacterList() {
        // Url to make a request
        val url = Constants.CONS_CHARACTER_LIST + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response
            val jsonResponse = response
            for (i in 0 until jsonResponse.length()) {
                val jsonCharacter = jsonResponse.getJSONObject(i)

                val member:Member = Member()
                member._id = jsonCharacter.getString("_id")
                member.name = jsonCharacter.getString("name")

                // Add processed character to the list
                memberList?.add(member)
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
