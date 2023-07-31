package com.dev.kotlincustomcalendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dev.kotlincustomcalendar.calendarUtils.CalendarCustomView
import com.dev.kotlincustomcalendar.calendarUtils.CalendarEventObjects
import com.dev.kotlincustomcalendar.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var start: Date? = null
    var end: Date? = null
    var initialDate: Date? = null
    var lastDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        setCalendarView()
    }


    private fun setCalendarView() {
        //Custom Event
        val eventObject = CalendarEventObjects(
            1,
            "Book",
            Date(),
            ContextCompat.getColor(this, R.color.duskYellow)
        )
        val mEvent: ArrayList<CalendarEventObjects> = ArrayList()
        mEvent.add(eventObject)

        val parent = binding.customCalendar.parent as ViewGroup
        parent.removeView(binding.customCalendar)
        binding.clCalendar.removeAllViews()
        binding.clCalendar.orientation = LinearLayout.VERTICAL

        val calendarCustomView = CalendarCustomView(this, mEvent)
        val mLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        calendarCustomView.layoutParams = mLayoutParams
        binding.clCalendar.addView(calendarCustomView)

        calendarCustomView.calendarGridView.setOnItemClickListener { adapterView, view, position, l ->
            if (adapterView.adapter.getView(l.toInt(), view, adapterView).alpha == 0.4F) {
                Log.d("hello", "hello")
            } else {
                val today = Calendar.getInstance()
                today.time = Date()

                val tapeDay = Calendar.getInstance()
                tapeDay.time = adapterView.adapter.getItem(l.toInt()) as Date

                val sameDay =
                    today.get(Calendar.YEAR) == tapeDay.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == tapeDay.get(
                        Calendar.DAY_OF_YEAR
                    )

                if (today.after(tapeDay) && !sameDay) {
                    Toast.makeText(
                        this,
                        "You can't select previous date.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (initialDate == null && lastDate == null) {
                        initialDate = adapterView.adapter.getItem(l.toInt()) as Date
                        lastDate = adapterView.adapter.getItem(l.toInt()) as Date
                    } else {
                        initialDate = lastDate
                        lastDate = adapterView.adapter.getItem(l.toInt()) as Date
                    }

                    if (initialDate != null && lastDate != null) {
                        calendarCustomView.setRangesOfDate(makeDateRange())
                    }
                }
            }
        }

    }

    private fun makeDateRange(): ArrayList<CalendarEventObjects> {
        if (lastDate!!.after(initialDate)) {
            start = initialDate
            end = lastDate
        } else {
            start = lastDate
            end = initialDate
        }
        val eventObject = ArrayList<CalendarEventObjects>()

        val gcal = GregorianCalendar()
        gcal.time = start

        while (!gcal.time.after(end)) {
            val d = gcal.time
            val calendarEventObjects =
                CalendarEventObjects(0, "", d, ContextCompat.getColor(this, R.color.duskYellow))
            eventObject.add(calendarEventObjects)
            gcal.add(Calendar.DATE, 1)
        }
        return eventObject
    }
}