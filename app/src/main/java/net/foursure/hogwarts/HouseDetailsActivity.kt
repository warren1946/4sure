package net.foursure.hogwarts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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
import net.foursure.hogwarts.models.House
import net.foursure.hogwarts.models.Member
import net.foursure.hogwarts.utils.Constants
import org.json.JSONException

class HouseDetailsActivity : AppCompatActivity() {
    // Global variables
    val LOG:String = "MainActivity"
    private var requestQueue: RequestQueue? = null
    private val HOUSE_ID = "house_id"
    private var house_id:String?=null

    // Recycler view and shimmer layout
    private var recyclerView: RecyclerView?=null
    private var shimmerLayout: ShimmerFrameLayout?=null
    private var rvAdapter: MemberRvAdapter?=null
    private var memberList: ArrayList<Member>?=null

    private lateinit var linearLayoutManager: LinearLayoutManager

    // House details textviews to update values
    private var tv_house:TextView?=null
    private var tv_mascot:TextView?=null
    private var tv_head_of_house:TextView?=null
    private var tv_house_ghost:TextView?=null
    private var tv_founder:TextView?=null
    private var tv_school:TextView?=null
    private var tv_values:TextView?=null
    private var tv_colours:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details)

        // Display back button on the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Get selected house id from previous screen
        house_id = intent.getStringExtra(HOUSE_ID)

        // Initialize the request queue
        requestQueue = Volley.newRequestQueue(this)

        // initialize house details textviews
        tv_house = findViewById<TextView>(R.id.tv_house)
        tv_mascot = findViewById<TextView>(R.id.tv_mascot)
        tv_head_of_house = findViewById<TextView>(R.id.tv_head_of_house)
        tv_house_ghost = findViewById<TextView>(R.id.tv_house_ghost)
        tv_founder = findViewById<TextView>(R.id.tv_founder)
        tv_school = findViewById<TextView>(R.id.tv_school)
        tv_values = findViewById<TextView>(R.id.tv_values)
        tv_colours = findViewById<TextView>(R.id.tv_colours)

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

        // Populate data
        getHouse(house_id!!)
    }

    fun getHouse(_id: String) {
        // Url to make a request
        val url = Constants.CONS_HOUSE_BY_ID + _id + "?key=" + Constants.CONS_API_KEY
        // Make api call
        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response
            for (i in 0 until response.length()) {
                // Get house object on current index
                val jsonHouse = response.getJSONObject(i)
                // Create house object and populate it with data
                val house: House = House()
                house._id = jsonHouse.getString("_id")
                house.name = jsonHouse.getString("name")
                house.mascot = jsonHouse.getString("mascot")
                house.headOfHouse = jsonHouse.getString("headOfHouse")
                house.houseGhost = jsonHouse.getString("houseGhost")
                house.founder = jsonHouse.getString("founder")

                // Check if json object has a school node
                if(jsonHouse.has("school"))
                    house.school = jsonHouse.getString("school")

                // Process members
                var members = ArrayList<Member>()
                var jsonMembers = jsonHouse.getJSONArray("members")

                if(jsonMembers.length() > 0) {
                    for (i in 0 until jsonMembers.length()) {
                        // Get member at current index
                        val memberJSONObject = jsonMembers.getJSONObject(i)
                        // Create new member and populate member with data
                        val objMember: Member = Member()
                        objMember._id = memberJSONObject.getString("_id")
                        objMember.name = memberJSONObject.getString("name")

                        // Add members to the main list
                        memberList?.add(objMember)
                    }
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

                // Assign received data to their appropriate textviews
                tv_house?.setText(house.name)
                tv_mascot?.setText(house.mascot)
                tv_head_of_house?.setText(house.headOfHouse)
                tv_house_ghost?.setText(house.houseGhost)
                tv_founder?.setText(house.founder)
                tv_school?.setText(house.school)
                tv_values?.setText(house.values.joinToString())
                tv_colours?.setText(house.colors.joinToString())

                // Update adapter with new data and close shimmer
                rvAdapter?.notifyDataSetChanged()
                shimmerLayout?.stopShimmer()
                shimmerLayout?.visibility = View.GONE
            }

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
