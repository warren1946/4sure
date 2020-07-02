package net.foursure.hogwarts

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import net.foursure.hogwarts.adapter.HouseRvAdapter
import net.foursure.hogwarts.models.House
import net.foursure.hogwarts.models.Member
import net.foursure.hogwarts.utils.AppDialog
import net.foursure.hogwarts.utils.Constants
import net.foursure.hogwarts.utils.NetworkManager
import org.json.JSONException

class HouseActivity : AppCompatActivity() {
    //Global variables
    val LOG:String = "HouseActivity"
    private var requestQueue: RequestQueue? = null
    // Recycler view and shimmer layout
    private var recyclerView: RecyclerView?=null
    private var shimmerLayout: ShimmerFrameLayout?=null
    private var rvAdapter: HouseRvAdapter?=null
    private var houseList: ArrayList<House>?=null

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house)

        // Display back button on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Initial recycler view and shimmer layout
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        shimmerLayout = findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        shimmerLayout?.startLayoutAnimation() // Start animation

        // initialize layout manager
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager

        // Initialize adapter and pass the values to RvAdapter
        houseList = ArrayList<House>()
        rvAdapter = HouseRvAdapter(houseList!!, this)
        // set the recyclerView to the adapter
        recyclerView?.adapter = rvAdapter;

        // Request spell list from the api textview
        requestQueue = Volley.newRequestQueue(this)

        // Check for internet connection before making APi calls
        if(NetworkManager().isOnline(this)) {
            getHouseList()
        }else{
            AppDialog().showConnectionErrorMessage(this)
        }

    }

    fun getHouseList() {
        // Url to make a request
        val url = Constants.CONS_HOUSE_LIST + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response and add data to the list
            for (i in 0 until response.length()) {
                val jsonHouse = response.getJSONObject(i)
                val house:House = House()
                house._id = jsonHouse.getString("_id")
                house.name = jsonHouse.getString("name")
                house.mascot = jsonHouse.getString("mascot")
                house.headOfHouse = jsonHouse.getString("headOfHouse")
                house.houseGhost = jsonHouse.getString("houseGhost")
                house.founder = jsonHouse.getString("founder")
                if(jsonHouse.has("school"))
                    house.school = jsonHouse.getString("school")

                // Process members
                var members = ArrayList<Member>()
                var jsonMembers = jsonHouse.getJSONArray("members")

                if(jsonMembers.length() > 0) {
                    for (i in 0 until jsonMembers.length()) {
                        val objMember: Member = Member();
                        objMember._id = jsonMembers.get(i).toString()
                        members.add(objMember)
                    }
                    house.members = members
                }

                // Process values
                var values = ArrayList<String>()
                var jsonValues = jsonHouse.getJSONArray("values")

                if(jsonValues.length() > 0) {
                    for (i in 0 until jsonValues.length()) {
                        values.add(jsonValues.get(i).toString())
                    }
                    house.values = values
                }

                // Process colors
                var lst_colors = ArrayList<String>()
                var jsonColors = jsonHouse.getJSONArray("colors")

                if(jsonColors.length() > 0) {
                    for (i in 0 until jsonColors.length()) {
                        lst_colors.add(jsonColors.get(i).toString())
                    }
                    house.colors = lst_colors
                }

                // Add processed house to the list
                houseList?.add(house)
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
