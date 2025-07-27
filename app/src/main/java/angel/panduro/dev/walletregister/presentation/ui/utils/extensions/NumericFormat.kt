package angel.panduro.dev.walletregister.presentation.ui.utils.extensions

import android.icu.text.DecimalFormat
import angel.panduro.dev.walletregister.presentation.ui.utils.constance.FormatNumber.FORMAT_WITH_COMA

fun String.formatNumber(format: String = FORMAT_WITH_COMA): String {
    val safeNumber = this.replace(Regex("[^\\d.]"), "")
    if(safeNumber.isBlank()) return "0.00"
    return DecimalFormat(format).format(safeNumber)
}

fun Float.formatNumber(format: String = FORMAT_WITH_COMA): String {
    return DecimalFormat(format).format(this)
}