package angel.panduro.dev.walletregister.data.dto

data class CardInformationDto(
    val id: Long = 0,
    val nameCard: String,
    val creditLineCard: String,
    val typeMoney: String,
    val paidDayExpired: Int,
    val dayClose: Int,
    val colorCard: ULong
)