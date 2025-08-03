package angel.panduro.dev.walletregister.presentation.ui.utils.extensions

fun String.toSafeFloat(): Float{
    val numericStr = this.replace(Regex("[^\\d.]"), "")
    if(numericStr.isBlank()) return 0.0f
    return numericStr.toFloat()
}

fun String.toSafeInt(): Int{
    val numericStr = this.replace(Regex("\\D"), "")
    if(numericStr.isBlank()) return 0
    return numericStr.toInt()
}