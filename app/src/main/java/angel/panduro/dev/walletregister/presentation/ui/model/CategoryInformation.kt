package angel.panduro.dev.walletregister.presentation.ui.model

import androidx.compose.ui.graphics.Color

data class CategoryInformation(
    val name: String,
    val icon: Int,
    val color: Color,
    val typeMoney: String = "PEN",
    var value: Float = 0.0f
)