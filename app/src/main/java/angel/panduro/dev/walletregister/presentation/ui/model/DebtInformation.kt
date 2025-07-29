package angel.panduro.dev.walletregister.presentation.ui.model

data class DebtInformation(
    val id: Long = 0,
    val idWallet: Long,
    val nameCard: String,
    val typeMoney: String,
    val debt: Float,
    val type: String,
    val quotePaid: Int = 0,
    val quotas: Int,
    val isPaid: Int,
    val date: Long,
    val dateExpired: Long
)
