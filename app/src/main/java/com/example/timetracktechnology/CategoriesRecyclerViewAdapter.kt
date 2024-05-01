package com.example.timetracktechnology

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesRecyclerViewAdapter(context: Context, private val categoriesList: ArrayList<Category>):
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesRecyclerViewAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row_categories, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: CategoriesRecyclerViewAdapter.MyViewHolder,
        position: Int
    ) {
        holder.tvTitle.setText(categoriesList.get(position).title)
        holder.tvDescription.setText(categoriesList.get(position).description)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

}