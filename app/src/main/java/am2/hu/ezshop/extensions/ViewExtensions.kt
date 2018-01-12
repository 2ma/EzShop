package am2.hu.ezshop.extensions

import android.graphics.Color
import android.graphics.Paint
import android.widget.TextView


fun TextView.setCompleted(completed: Boolean, color: Int) {
    if (completed) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        setTextColor(Color.LTGRAY)
    } else {
        paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        setTextColor(color)
    }
}