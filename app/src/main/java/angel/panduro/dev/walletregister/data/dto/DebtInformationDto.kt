package angel.panduro.dev.walletregister.data.dto

data class DebtInformationDto(
    val id: Long = 0,
    val idWallet: Long,
    val nameCard: String,
    val typeMoney: String,
    val debt: Float,
    val category: String,
    val quotePaid: Int,
    val quotas: Int,
    val isPaid: Int,
    val date: Long,
    val dateExpired: Long
)
