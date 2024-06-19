package com.example.instagram.common.util

import kotlinx.datetime.LocalDateTime

object DateFormatter {
    fun parseDate(dateString: String): String{
        return try {
            val parsedDateTime = LocalDateTime.parse(dateString)
            "${
                parsedDateTime.month.name.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            } ${parsedDateTime.dayOfMonth}, ${parsedDateTime.time.hour}:${parsedDateTime.time.minute}"
        }catch (error: IllegalArgumentException){
            dateString
        }
    }
}