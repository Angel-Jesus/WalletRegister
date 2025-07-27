package angel.panduro.dev.walletregister.presentation.ui.utils.constance

import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.EntertaimentColor
import angel.panduro.dev.walletregister.presentation.ui.theme.FoodColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ServiceColor
import angel.panduro.dev.walletregister.presentation.ui.theme.ShoppingColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TransportationColor

object Categories {
    val Debt = listOf(
        CategoryInformation(
            name = "Comida y Bebida",
            icon = R.drawable.category_food,
            color = FoodColor,
        ),
        CategoryInformation(
            name = "Compras",
            icon = R.drawable.category_shoppin,
            color = ShoppingColor
        ),
        CategoryInformation(
            name = "Transporte",
            icon = R.drawable.category_transportation,
            color = TransportationColor
        ),
        CategoryInformation(
            name = "Vida y Entretenimiento",
            icon = R.drawable.category_entertainment,
            color = EntertaimentColor
        ),
        CategoryInformation(
            name = "Servicios",
            icon = R.drawable.category_service,
            color = ServiceColor
        )
    )
}