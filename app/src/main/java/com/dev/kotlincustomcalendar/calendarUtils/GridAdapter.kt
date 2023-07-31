package com.dev.kotlincustomcalendar.calendarUtils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.dev.kotlincustomcalendar.R
import java.util.*

class GridAdapter(
    private val context: Context,
    private val monthlyDates: List<Date>,
    private val resource: Int,
    private val currentDate: Calendar,
    private val allEvents: List<CalendarEventObjects>,
) : ArrayAdapter<Date>(context, resource, monthlyDates) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val date = monthlyDates[position]
        val dateCal = Calendar.getInstance()
        dateCal.time = date

        val dayValue = dateCal.get(Calendar.DAY_OF_MONTH)
        val displayMonth = dateCal.get(Calendar.MONTH) + 1
        val displayYear = dateCal.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1
        val currentYear = currentDate.get(Calendar.YEAR)

        var cellView: View? = convertView

        if (cellView == null) {
            cellView =
                LayoutInflater.from(context).inflate(R.layout.single_cell_layout, parent, false)
        }

        if (displayMonth == currentMonth && displayYear == currentYear) {
            cellView?.setBackgroundColor(Color.parseColor("#F5F5F5"))
        } else {
            cellView?.alpha = 0.4F
        }

        //Add day to calendar
        val calendarDate = cellView?.findViewById<TextView>(R.id.calendar_date_id)
        calendarDate?.text = dayValue.toString()

        //Add event to the calendar
        val eventIndicator = cellView?.findViewById<TextView>(R.id.event_id)
        val eventCalendar = Calendar.getInstance()

        allEvents.map {
            eventCalendar.time = it.date
            if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1) {
                eventIndicator?.text = it.message
                cellView?.setBackgroundColor(
                    if (it.color != 0) it.color else Color.parseColor(
                        "#FF0000"
                    )
                )
            }
        }

        return cellView!!
    }

    override fun getCount(): Int {
        return monthlyDates.size
    }

    override fun getItem(position: Int): Date? {
        return monthlyDates[position]
    }

    override fun getPosition(item: Date?): Int {
        return monthlyDates.indexOf(item)
    }

}