package angel.panduro.dev.walletregister.domain.model

data class DebtInformationModel(
    val id: Long = 0,
    val idWallet: Long,
    val nameCard: String,
    val typeMoney: String,
    val debt: Float,
    val category: String,
    val quotePaid: Int,
    val quote: Int,
    val isPaid: Int,
    val date: Long,
    val dateExpired: Long
)
