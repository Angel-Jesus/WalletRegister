package angel.panduro.dev.walletregister.presentation.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class CardInformation(
    val id: Long = 0,
    val nameCard: String,
    val creditLineCard: String,
    val typeMoney: String,
    val paidDateExpired: Int,
    val dateClose: Int,
    val colorCard: ULong
)
