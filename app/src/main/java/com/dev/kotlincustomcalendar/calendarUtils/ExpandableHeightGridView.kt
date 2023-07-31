package com.dev.kotlincustomcalendar.calendarUtils

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.GridView

class ExpandableHeightGridView : GridView {

    private var expanded = false


    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    private fun isExpanded(): Boolean {
        return expanded
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isExpanded()) {
            val expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST)

            super.onMeasure(widthMeasureSpec, expandSpec)

            val params = layoutParams as ViewGroup.LayoutParams

            params.height = measuredHeight
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }

    fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

}
