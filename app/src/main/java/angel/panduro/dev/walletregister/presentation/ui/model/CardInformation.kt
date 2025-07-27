package angel.panduro.dev.walletregister.presentation.ui.model

data class CardInformation(
    val id: Long = 0,
    val nameCard: String,
    val creditLineCard: String,
    val typeMoney: String,
    val paidDateExpired: Int,
    val dateClose: Int,
    val colorCard: ULong
)
