package net.foursure.hogwarts.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.foursure.one.hogwarts.R
import net.foursure.hogwarts.models.Spell

class SpellsRvAdapter(var spellList: ArrayList<Spell>, var _context:Context) : RecyclerView.Adapter<SpellsRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        // Inflate custom list item
        val v = LayoutInflater.from(viewGroup?.context).inflate(R.layout.spell_list_item, viewGroup, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return spellList.size // Spell list count
    }

    override fun onBindViewHolder(holder:ViewHolder, index: Int) {
        // Fill list item with data
        holder.tv_spell?.text = spellList[index].spell
        holder.tv_type?.text = Html.fromHtml("<b>Type:</b> " + spellList[index].type)
        holder.tv_effect?.text = Html.fromHtml("<b>Effect:</b> " + spellList[index].effect)

        holder.itemView.setOnClickListener {
            Toast.makeText(_context, spellList[index].spell, Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val tv_spell = itemView.findViewById<TextView>(R.id.tv_spell)
        val tv_type = itemView.findViewById<TextView>(R.id.tv_type)
        val tv_effect = itemView.findViewById<TextView>(R.id.tv_effect)
    }
}