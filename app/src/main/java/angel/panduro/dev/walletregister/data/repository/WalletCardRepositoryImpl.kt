package angel.panduro.dev.walletregister.data.repository

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.core.base.repository.BaseRoom
import angel.panduro.dev.walletregister.data.dto.CardInformationDto
import angel.panduro.dev.walletregister.data.local.database.dao.CardsWalletDao
import angel.panduro.dev.walletregister.data.mapper.toDto
import angel.panduro.dev.walletregister.data.mapper.toEntity
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletCardRepositoryImpl(
    private val cardsWalletDao: CardsWalletDao
): BaseRoom(), WalletCardRepository {
    override suspend fun saveCard(cardInformation: CardInformationDto): EitherWallet<Failure, Unit> {
        return safeBaseRoom { cardsWalletDao.insertCardsWallet(cardInformation.toEntity()) }
    }

    override fun getAllCards(): Flow<List<CardInformationDto>> {
        return cardsWalletDao.getFlowCardsWallet().map { it.toDto() }
    }
}