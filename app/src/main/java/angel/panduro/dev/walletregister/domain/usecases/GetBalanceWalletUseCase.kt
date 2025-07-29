package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.Categories
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.sumOfFloat

class GetBalanceWalletUseCase(
    private val debtRepository: WalletDebtRepository
): BaseUseCase<GetBalanceWalletUseCase.Params, Map<String, CategoryInformation>>() {
    data class Params(val idCard: Long)

    override suspend fun run(params: Params): EitherWallet<Failure, Map<String, CategoryInformation>> {
        val debtByCategory = mutableMapOf<String, CategoryInformation>()
        val debtCard = debtRepository.getDebtsWallet(params.idCard)
        when(debtCard){
            is EitherWallet.Error<Failure> -> return EitherWallet.Error(debtCard.value)
            is EitherWallet.Success<List<DebtInformationDto>> -> {
                val debtByType = debtCard.value.groupBy { it.category }

                debtByType.forEach {(category, debt) ->
                    val debtSum = debt.sumOfFloat {
                        if (it.quotas > 1) {
                            it.debt / it.quotas
                        } else {
                            it.debt
                        }
                    }

                    debtByCategory[category] = CategoryInformation(
                        name = category,
                        icon = Categories.getIconByDescription(category),
                        color = Categories.getColorByDescription(category),
                        typeMoney = "PEN",
                        value = debtSum
                    )
                }
                return EitherWallet.Success(debtByCategory)
            }
        }
    }
}