package com.shaparapatah.tomatostopwwatch

internal class TimestampMillisecondsFormatter() {

    companion object {

        const val DEFAULT_TIME = "00.00.00"
    }

    fun format(timestamp: Long): String {
        val millisecondsFormatted = (timestamp % 100).pad(2)
        val seconds = timestamp / 1000
        val secondsFormatted = (seconds % 60).pad(2)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(2)
        val hours = minutes / 60
        return if (hours > 0) {
            val hoursFormatted = (minutes / 60).pad(2)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}
