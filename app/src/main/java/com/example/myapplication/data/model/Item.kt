package com.example.myapplication.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.joda.time.DateTime
import org.joda.time.Interval
import java.time.Period

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "id") val id: Int,
    @Json(name = "deleted") val isDeleted: Boolean = false,
    @Json(name = "type") val type: String,
    @Json(name = "by") val authorName: String?,
    @Json(name = "time") val publishDate: Int?,
    @Json(name = "dead") val isDead: Boolean = false,
    @Json(name = "kids") val kids: List<Int>?,
    @Json(name = "parent") val parent: Int?,
    @Json(name = "text") val text: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "parts") val parts: List<Int>?,
    @Json(name = "score") val score: Int?,
    @Json(name = "descendants") val descendants: Int = 0
) {

    fun getElapsedTime() : String? {
        this.publishDate?.let {
            val now = DateTime();
            val posted = DateTime(it.toLong() * 1000)

            val interval = Interval(posted, now)
            val period = interval.toPeriod()

            val years = period.years
            val months = period.months
            val weeks = period.weeks
            val days = period.days
            val hours = period.hours
            val minutes = period.minutes
            val seconds = period.seconds

            if (years > 0) {
                return String.format("%s%s", years, YEARS)
            } else if (months > 0) {
                return String.format("%s%s", months, MONTHS)
            } else if (weeks > 0) {
                return String.format("%s%s", weeks, WEEKS)
            } else if (days > 0) {
                return String.format("%s%s", days, DAYS)
            } else if (hours > 0) {
                return String.format("%s%s", hours, HOURS)
            } else if (minutes > 0) {
                return String.format("%s%s", minutes, MINUTES)
            } else {
                return String.format("%s%s", seconds, SECONDS)
            }
        }
        return publishDate.toString()
    }

    companion object {
        private const val YEARS = "y"
        private const val MONTHS = "M"
        private const val WEEKS = "w"
        private const val DAYS = "d"
        private const val HOURS = "h"
        private const val MINUTES = "m"
        private const val SECONDS = "s"


    }

}