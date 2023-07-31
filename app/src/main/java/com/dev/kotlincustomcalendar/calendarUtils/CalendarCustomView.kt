package com.dev.kotlincustomcalendar.calendarUtils

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dev.kotlincustomcalendar.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarCustomView :
    LinearLayout, CalendarInterface {

    private lateinit var previousButton: ImageView

    private lateinit var nextButton: ImageView

    private lateinit var currentDate: TextView

    lateinit var calendarGridView: ExpandableHeightGridView

    private val MAX_CALENDAR_COLUMN = 42

    private val formatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)

    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private lateinit var context: Context

    private lateinit var mAdapter: GridAdapter

    private var eventObjects: ArrayList<CalendarEventObjects> = ArrayList()

    constructor(context: Context?, eventObjectses: ArrayList<CalendarEventObjects>) : super(
        context
    ) {
        this.eventObjects = eventObjectses
        this.context = context!!
        initializeUILayout()
        setUpCalendarAdapter()
        setPreviousButtonClickEvent()
        setNextButtonClickEvent()
        setGridCellClickEvents()
        setCurrentDateClickEvent()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.context = context!!
        initializeUILayout()
        setPreviousButtonClickEvent()
        setNextButtonClickEvent()
        setGridCellClickEvents()
        setCurrentDateClickEvent()
        Log.d(CalendarCustomView::class.java.name, "I need to call this method")
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun setCurrentDateClickEvent() {
        currentDate.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val date: Calendar

            date = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                context,
                { view, year, monthOfYear, dayOfMonth ->
                    cal.set(year, monthOfYear, dayOfMonth)
                    setUpCalendarAdapter()
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DATE)
            )
            datePickerDialog.show()
        }
    }

    private fun setGridCellClickEvents(): String? {
        val text = arrayOfNulls<String>(1)

        return text[0]
    }

    private fun setNextButtonClickEvent() {
        nextButton.setOnClickListener {
            nextMonth()
        }
    }

    private fun setPreviousButtonClickEvent() {
        previousButton.setOnClickListener {
            previousMonth()
        }

    }

    private fun setUpCalendarAdapter() {
        val dayValueInCells: ArrayList<Date> = ArrayList()
        val mCal = cal.clone() as Calendar
        mCal.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth)
        while (dayValueInCells.size < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.time)
            mCal.add(Calendar.DAY_OF_MONTH, 1)
        }
        Log.d(CalendarCustomView::class.java.name, "Number of date " + dayValueInCells.size)
        val sDate = formatter.format(cal.time)
        currentDate.text = sDate
        mAdapter =
            GridAdapter(context, dayValueInCells, R.layout.single_cell_layout, cal, eventObjects)
        calendarGridView.adapter = mAdapter
    }

    private fun initializeUILayout() {
        try {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.calender_layout, this)
            previousButton = view.findViewById<View>(R.id.previous_month) as ImageView
            nextButton = view.findViewById<View>(R.id.next_month) as ImageView
            currentDate = view.findViewById<View>(R.id.display_current_date) as TextView
            calendarGridView =
                view.findViewById<View>(R.id.calendar_grid) as ExpandableHeightGridView
            calendarGridView.setExpanded(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun nextMonth() {
        cal.add(Calendar.MONTH, 1)
        val eventObject = CalendarEventObjects(
            1,
            "Book",
            Date(),
            ContextCompat.getColor(context, R.color.duskYellow)
        )
        val mEvent: ArrayList<CalendarEventObjects> = ArrayList()
        mEvent.add(eventObject)
        this.eventObjects=mEvent
        setUpCalendarAdapter()
    }

    override fun previousMonth() {
        cal.add(Calendar.MONTH, -1)
        val eventObject = CalendarEventObjects(
            1,
            "Book",
            Date(),
            ContextCompat.getColor(context, R.color.duskYellow)
        )
        val mEvent: ArrayList<CalendarEventObjects> = ArrayList()
        mEvent.add(eventObject)
        this.eventObjects=mEvent
        setUpCalendarAdapter()
    }

    fun setRangesOfDate(eventObject: ArrayList<CalendarEventObjects>) {
        this.eventObjects = eventObject
        setUpCalendarAdapter()
        mAdapter.notifyDataSetChanged()
    }

}