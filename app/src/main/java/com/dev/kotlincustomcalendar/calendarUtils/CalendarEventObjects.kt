package com.dev.kotlincustomcalendar.calendarUtils

import java.util.Date

data class CalendarEventObjects(
    var id:Int,
    var message:String,
    var date:Date,
    var color:Int
)
