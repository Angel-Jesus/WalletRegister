package angel.panduro.dev.walletregister.presentation.ui.utils.enums

enum class DebtTapOptionEnum(val title: String) {
    DEBT_NOT_PAID("Deudas pendientes"),
    DEBT_PAID("Deudas pagadas");

    companion object{
        val sections: List<DebtTapOptionEnum> = entries
        val sectionsTitle: List<String> = sections.map { it.title }
    }

}