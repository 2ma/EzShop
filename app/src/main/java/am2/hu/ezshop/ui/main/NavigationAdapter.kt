package am2.hu.ezshop.ui.main

import am2.hu.ezshop.R
import am2.hu.ezshop.persistance.entity.ListName
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.nav_item.view.*


class NavigationAdapter(context: Context, private val menuClick: (ListName) -> Unit) : RecyclerView.Adapter<NavigationAdapter.NavViewHolder>() {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var menuItems: List<ListName> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var selectedShop = ""
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.onBindMenu(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NavViewHolder {
        val view = layoutInflater.inflate(R.layout.nav_item, parent, false)

        return NavViewHolder(view)
    }

    inner class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.menuName.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            menuClick(menuItems[adapterPosition])

        }

        fun onBindMenu(menu: ListName) {
            itemView.menuName.text = menu.listName
            itemView.menuName.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (menu.listName == selectedShop) R.drawable.ic_done_24dp else 0, 0)
        }
    }
}