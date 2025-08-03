package angel.panduro.dev.walletregister.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CurrencyEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.getDateExpired
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.getDateNow
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.toSafeFloat
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.toSafeInt

class SaveDebtUseCase(
    private val cardRepository: WalletCardRepository,
    private val debtRepository: WalletDebtRepository
): BaseUseCase<SaveDebtUseCase.Params, Unit>() {
    data class Params(
        val idCard: Long,
        val account: String,
        val category: String,
        val quote: String,
        val debt: String
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun run(params: Params) {
        val cardInformation = cardRepository.getCard(params.idCard)
        cardInformation?.let {
            val debtInformationDto = DebtInformationDto(
                idWallet = params.idCard,
                nameCard = params.account,
                typeMoney = CurrencyEnum.SOL_PERUANO.code,
                debt = params.debt.toSafeFloat(),
                category = params.category,
                quotePaid = 0,
                quote = params.quote.toSafeInt(),
                isPaid = 0,
                date = getDateNow(),
                dateExpired = getDateExpired(cardInformation.paidDayExpired, cardInformation.dayClose)
            )
            return debtRepository.insertDebt(debtInformationDto)
        }
    }
}