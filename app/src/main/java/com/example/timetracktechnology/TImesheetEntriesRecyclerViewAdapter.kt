package com.example.timetracktechnology

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream



class TImesheetEntriesRecyclerViewAdapter(
    context: Context,
    private val timesheetEntriesList: ArrayList<TimesheetEntry>
):
    RecyclerView.Adapter<TImesheetEntriesRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
        val tvEndTime: TextView = itemView.findViewById(R.id.tvEndTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TImesheetEntriesRecyclerViewAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row_timesheet_entry, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TImesheetEntriesRecyclerViewAdapter.MyViewHolder,
        position: Int
    ) {
        holder.tvTitle.setText(timesheetEntriesList.get(position).title)
        holder.tvCategory.setText(timesheetEntriesList.get(position).category)
        holder.tvDate.setText(timesheetEntriesList.get(position).entryDate.toString())
        holder.tvStartTime.setText(timesheetEntriesList.get(position).startTime.toString())
        holder.tvEndTime.setText(timesheetEntriesList.get(position).endTime.toString())
    }

    override fun getItemCount(): Int {
        return timesheetEntriesList.size
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}