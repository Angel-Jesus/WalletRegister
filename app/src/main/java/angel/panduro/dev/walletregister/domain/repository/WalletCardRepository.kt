package angel.panduro.dev.walletregister.domain.repository

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.data.dto.CardInformationDto
import kotlinx.coroutines.flow.Flow

interface WalletCardRepository {
    suspend fun saveCard(cardInformation: CardInformationDto): EitherWallet<Failure, Unit>
    suspend fun deleteCard(idCard: Long): EitherWallet<Failure, Unit>
    fun getAllCards(): Flow<List<CardInformationDto>>

}