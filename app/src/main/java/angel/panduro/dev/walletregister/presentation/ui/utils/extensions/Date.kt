package angel.panduro.dev.walletregister.presentation.ui.utils.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Long.millisToDateString(pattern: String = "dd/MM/yyyy"): String{
    return LocalDate.ofEpochDay(this)
        .format(DateTimeFormatter.ofPattern(pattern))
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateNow(): Long{
    return LocalDate.now().toEpochDay()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateExpired(dayExpired: Int, dayClose: Int): Long {
    val today = LocalDate.now()
    val safeDay = dayExpired.coerceAtMost(today.lengthOfMonth())
    val dateThisMonth = LocalDate.of(today.year, today.month, safeDay)

    return if (today.dayOfMonth > dayClose) {
        dateThisMonth.plusMonths(2).toEpochDay()
    } else {
        dateThisMonth.plusMonths(1).toEpochDay()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.convertDateToMillis(): Long{
    return LocalDate.ofEpochDay(this)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}