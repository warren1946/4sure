package net.foursure.hogwarts.adapter

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.models.Character
import net.foursure.hogwarts.models.Member
import net.foursure.hogwarts.utils.AppDialog
import net.foursure.hogwarts.utils.Constants
import net.foursure.hogwarts.utils.NetworkManager
import org.json.JSONException

class MemberRvAdapter(var memberList: ArrayList<Member>, var _context: Context) : RecyclerView.Adapter<MemberRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MemberRvAdapter.ViewHolder {
        // Inflate custom list item
        val v = LayoutInflater.from(viewGroup?.context).inflate(R.layout.member_list_item, viewGroup, false)
        return MemberRvAdapter.ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return memberList.size // Spell list count
    }

    override fun onBindViewHolder(holder: MemberRvAdapter.ViewHolder, index: Int) {
        // Fill list item with data
        holder.tv_name?.text = memberList[index].name

        holder.itemView.setOnClickListener {
            // Check for internet connection before making APi calls
            if(NetworkManager().isOnline(_context)) {
                // Display member details dialog
                getCharacter(memberList[index]._id.toString())
            }else{
                AppDialog().showConnectionErrorMessage(_context)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
    }

    // Create and show a custom dialog for member/character details
    fun showMemberDialog(member: Character) {
        // Initialize dialog
        val dialog = Dialog(_context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.member_details_layout)

        // Initialize dialog's member/charater textviews and populate them with data
        val tvName = dialog?.findViewById<TextView>(R.id.tv_name)
        tvName?.text = member.name

        val tvRole = dialog?.findViewById<TextView>(R.id.tv_role)
        tvRole?.text = member.role

        val tvHouse = dialog?.findViewById<TextView>(R.id.tv_house)
        tvHouse?.text = member.house

        val tvSchool = dialog?.findViewById<TextView>(R.id.tv_school)
        tvSchool?.text = member.school

        val tvMinistry = dialog?.findViewById<TextView>(R.id.tv_ministry)
        tvMinistry?.text = member.ministryOfMagic.toString()

        val tvOrder = dialog?.findViewById<TextView>(R.id.tv_order)
        tvOrder?.text = member.orderOfThePhoenix.toString()

        val tvArmy = dialog?.findViewById<TextView>(R.id.tv_army)
        tvArmy?.text = member.dumbledoresArmy.toString()

        val tvDeathEater = dialog?.findViewById<TextView>(R.id.tv_death_eater)
        tvDeathEater?.text = member.deathEater.toString()

        val tvBloodStatus = dialog?.findViewById<TextView>(R.id.tv_blood_status)
        tvBloodStatus?.text = member.bloodStatus

        val tvSpecies = dialog?.findViewById<TextView>(R.id.tv_species)
        tvSpecies?.text = member.species


        // Initialize dialog's close button and listen for click events
        val btn_close = dialog?.findViewById<Button>(R.id.btn_close)
        btn_close?.setOnClickListener {
            dialog.dismiss()
        }

        dialog?.show()
    }

    fun getCharacter(_id: String) {
        val progressDialog = ProgressDialog(_context)
        progressDialog.setMessage("Loading details, please wait ...")
        progressDialog.show()

        // Url to make a request
        val url = Constants.CONS_CHARACTER_BY_ID + _id + "?key=" + Constants.CONS_API_KEY
        // Make api call and request selected character/member details
        val requestQueue = Volley.newRequestQueue(_context)
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
            // Process api call response
            val character:Character = Character()
            if(response.has("_id"))
                character._id = response.getString("_id")

            if(response.has("name"))
                character.name = response.getString("name")

            if(response.has("role"))
                character.role = response.getString("role")

            if(response.has("house"))
                character.house = response.getString("house")

            if(response.has("school"))
                character.school = response.getString("school")

            if(response.has("ministryOfMagic"))
                character.ministryOfMagic = response.getBoolean("ministryOfMagic")

            if(response.has("orderOfThePhoenix"))
                character.orderOfThePhoenix = response.getBoolean("orderOfThePhoenix")

            if(response.has("dumbledoresArmy"))
                character.dumbledoresArmy = response.getBoolean("dumbledoresArmy")

            if(response.has("deathEater"))
                character.deathEater = response.getBoolean("deathEater")

            if(response.has("bloodStatus"))
                character.bloodStatus = response.getString("bloodStatus")

            if(response.has("species"))
                character.species = response.getString("species")

            showMemberDialog(character)

            //close the progress bar
            progressDialog.dismiss()

        } catch (e: JSONException) {
            Log.e("MemberRvAdapter", e.message)
            //close the progress bar
            progressDialog.dismiss()
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
}