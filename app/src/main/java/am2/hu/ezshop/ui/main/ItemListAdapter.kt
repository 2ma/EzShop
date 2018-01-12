package am2.hu.ezshop.ui.main

import am2.hu.ezshop.R
import am2.hu.ezshop.extensions.setCompleted
import am2.hu.ezshop.persistance.entity.Item
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*


class ItemListAdapter(context: Context, val itemClick: (Item) -> Unit) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    val lightBlack = ContextCompat.getColor(context, R.color.light_black)

    var itemList: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val view = layoutInflater.inflate(R.layout.item, parent, false)

        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val item = itemList[adapterPosition]
            val update = item.copy(completed = !item.completed)
            update.id = item.id
            itemClick(update)
        }

        fun onBind(item: Item) {
            itemView.item.text = item.itemName
            itemView.item.isChecked = item.completed
            itemView.item.setCompleted(item.completed, lightBlack)
        }

    }
}