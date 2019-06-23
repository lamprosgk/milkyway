package me.lamprosgk.milkyway.ui

import me.lamprosgk.milkyway.R
import java.net.UnknownHostException

object ErrorMapper {

    fun getMessage(throwable: Throwable): Int {
         return when (throwable) {
            is UnknownHostException -> R.string.message_error_connection
            else -> R.string.message_error_generic
        }
    }
}
