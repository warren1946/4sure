package net.foursure.hogwarts.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.HouseDetailsActivity
import net.foursure.hogwarts.models.House
import java.io.Serializable

class HouseRvAdapter(var houseList: ArrayList<House>, var _context: Context) : RecyclerView.Adapter<HouseRvAdapter.ViewHolder>() {
    // Global variables
    private val HOUSE_ID = "house_id"

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HouseRvAdapter.ViewHolder {
        // Inflate custom list item
        val v = LayoutInflater.from(viewGroup?.context).inflate(R.layout.list_item, viewGroup, false)
        return HouseRvAdapter.ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return houseList.size // Spell list count
    }

    override fun onBindViewHolder(holder: HouseRvAdapter.ViewHolder, index: Int) {
        // Fill list item with data
        holder.tv_name?.text = houseList[index].name
        holder.tv_details?.text = Html.fromHtml("<b>Founder:</b> " + houseList[index].founder)
        holder.reg_count?.text = Html.fromHtml(houseList[index].members.size.toString())

        holder.itemView.setOnClickListener {
            val intent = Intent(_context, HouseDetailsActivity::class.java)
            intent.putExtra(HOUSE_ID, houseList[index]._id)
            _context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        val tv_details = itemView.findViewById<TextView>(R.id.tv_details)
        val reg_count = itemView.findViewById<TextView>(R.id.reg_count)
    }
}