package angel.panduro.dev.walletregister.presentation.ui.utils.enums

import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.theme.EntertaimentColor
import angel.panduro.dev.walletregister.presentation.ui.theme.FoodColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ServiceColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ShoppingColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TransportationColor

enum class Categories(val description: String, val icon: Int, val color: Color) {
    FOOD(description = "Comida y Bebida", icon = R.drawable.category_food, color = FoodColor),
    SHOPPING(description = "Compras", icon = R.drawable.category_shoppin, color = ShoppingColor),
    TRANSPORTATION(description = "Transporte", icon = R.drawable.category_transportation, color = TransportationColor),
    ENTERTAINMENT(description = "Vida y Entretenimiento", icon = R.drawable.category_entertainment, color = EntertaimentColor),
    SERVICE(description = "Servicios", icon = R.drawable.category_service, color = ServiceColor);

    companion object{
        fun getIconByDescription(description: String): Int{
            return entries.firstOrNull { it.description == description }?.icon ?: FOOD.icon
        }
        fun getColorByDescription(description: String): Color{
            return entries.firstOrNull { it.description == description }?.color ?: FOOD.color
        }
        fun getCategoriesByDescription(description: String): Categories{
            return entries.firstOrNull { it.description == description } ?: FOOD
        }
    }
}