package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CategoriesEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.sumOfFloat

class GetBalanceWalletUseCase(
    private val debtRepository: WalletDebtRepository
): BaseUseCase<GetBalanceWalletUseCase.Params, Map<String, CategoryInformation>>() {
    data class Params(val idCard: Long)

    override suspend fun run(params: Params): Map<String, CategoryInformation> {
        val debtByCategory = mutableMapOf<String, CategoryInformation>()
        val debtCard = debtRepository.getDebtsWallet(params.idCard)
        val debtByType = debtCard.groupBy { it.category }

        debtByType.forEach {(category, debt) ->

            val debtSum = debt.sumOfFloat {
                if (it.quote > 1) {
                    it.debt / it.quote
                } else {
                    it.debt
                }
            }

            debtByCategory[category] = CategoryInformation(
                name = category,
                icon = CategoriesEnum.getIconByDescription(category),
                color = CategoriesEnum.getColorByDescription(category),
                typeMoney = "PEN",
                value = debtSum
            )
        }
        return debtByCategory
    }
}