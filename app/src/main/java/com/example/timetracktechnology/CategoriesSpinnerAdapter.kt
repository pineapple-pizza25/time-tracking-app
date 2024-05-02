package com.example.timetracktechnology

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CategoriesSpinnerAdapter(context: Context, private val categories: ArrayList<Category>) :
    ArrayAdapter<Category> (context, 0, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val category = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.categories_spinner_item, parent, false)

        val tvCategoryTitle = view.findViewById<TextView>(R.id.tvCategoryTitle)

       tvCategoryTitle.text = category?.title

        return view
    }
}