package com.am.core.util

import java.util.*

typealias DisplayableDate = String


fun getCurrentDisplayableDate(): DisplayableDate {
    val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    val month = Calendar.getInstance().get(Calendar.MONTH) + 1
    val year = Calendar.getInstance().get(Calendar.YEAR) - 2000

    val monthShort =
        Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

    return "$dayOfMonth $monthShort'$year"
}

fun getCurrentTimestamp(): Long {
    return Calendar.getInstance().timeInMillis
}