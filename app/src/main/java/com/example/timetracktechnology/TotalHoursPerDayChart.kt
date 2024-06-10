package com.example.timetracktechnology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.identity.CredentialDataResult.Entries
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.collection.LLRBNode.Color
import java.security.Timestamp
import java.time.LocalDate
import java.time.ZoneId

class TotalHoursPerDayChart : AppCompatActivity() {

    private lateinit var lineChart : LineChart

    private lateinit var entries: ArrayList<Entry>
    private lateinit var dateList: ArrayList<LocalDate>

    private lateinit var durationList: ArrayList<Long>
    private lateinit var dateAsFloatList: ArrayList<Float>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_hours_per_day_chart)

        //val filterEntryList = intent.getIntArrayExtra("filterEntryList") ?: intArrayOf()

        entries = ArrayList<Entry>()
        dateAsFloatList = ArrayList<Float>()

        lineChart = findViewById(R.id.lineChart)

        dateList  = intent.getSerializableExtra("dates") as ArrayList<LocalDate>
        durationList = intent.getSerializableExtra("durations") as ArrayList<Long>

        convertDateToFloat()

        getEntriesForChart()

        getLineDataSet()

        configureChart()

    }

    private fun getEntriesForChart() {
        for (i in dateAsFloatList.indices) {
            val date = dateAsFloatList[i]
            val duration = durationList[i]
            entries.add(Entry(date, duration.toFloat()))
        }
    }

    private fun getLineDataSet(){
        val color1 = ContextCompat.getColor(this, android.R.color.holo_purple)
        val color2 = ContextCompat.getColor(this, android.R.color.holo_blue_bright)
        val color3 = ContextCompat.getColor(this, android.R.color.holo_green_dark)

        val dataSet = LineDataSet(entries, "Minutes"); // add entries to dataset
        dataSet.setColor(color1)
        dataSet.valueTextColor = color2
        dataSet.lineWidth = 3f
        dataSet.setDrawCircleHole(false)
        dataSet.highLightColor = color3

        getLineData(dataSet)
    }

    private fun getLineData(dataSet: LineDataSet){
        val LineData = LineData(dataSet)
        lineChart.data = LineData
        lineChart.invalidate()
    }

    private fun convertDateToFloat(){
        for (date in dateList){

            val dateAsFloat = localDateToLong(date)
            dateAsFloatList.add(dateAsFloat.toFloat())
        }
    }

    private fun localDateToLong(localDate: LocalDate): Long {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun configureChart() {
        // X-Axis configuration
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f // Interval between labels
        xAxis.valueFormatter = XAxisDateFormatter() // Custom formatter for date labels
        xAxis.labelRotationAngle = -45f // Rotate labels if necessary
        xAxis.setDrawGridLines(false) // Disable grid lines for clarity

        // Y-Axis configuration (Left Axis)
        val leftAxis = lineChart.axisLeft
        leftAxis.setDrawGridLines(true) // Enable grid lines
        leftAxis.axisMinimum = 0f // Minimum value for the Y-Axis

        // Y-Axis configuration (Right Axis)
        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false // Disable the right Y-Axis

        // General chart configuration
        lineChart.description.text = "Total Hours Per Day" // Description text
        lineChart.setNoDataText("No data available") // Text to display when no data is available
        lineChart.setTouchEnabled(true) // Enable touch gestures
        lineChart.setPinchZoom(true) // Enable pinch zoom
    }

    inner class XAxisDateFormatter : com.github.mikephil.charting.formatter.ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val millis = value.toLong()
            val date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
            return date.toString() // Format date as desired
        }
    }
}