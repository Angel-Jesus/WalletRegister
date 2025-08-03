package angel.panduro.dev.walletregister.data.repository

import angel.panduro.dev.walletregister.data.dto.CardInformationDto
import angel.panduro.dev.walletregister.data.local.database.dao.CardsWalletDao
import angel.panduro.dev.walletregister.data.mapper.toDto
import angel.panduro.dev.walletregister.data.mapper.toEntity
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletCardRepositoryImpl(
    private val cardsWalletDao: CardsWalletDao
): WalletCardRepository {
    override suspend fun saveCard(cardInformation: CardInformationDto) {
        return cardsWalletDao.insertCardsWallet(cardInformation.toEntity())
    }

    override suspend fun deleteCard(idCard: Long) {
        return cardsWalletDao.deleteCard(idCard)
    }

    override suspend fun getCard(idCard: Long): CardInformationDto? {
        return cardsWalletDao.getCardWallet(idCard)?.toDto()
    }

    override suspend fun getAllCards(): List<CardInformationDto> {
        return cardsWalletDao.getCardsWallet().toDto()
    }

    override fun getAllCardsFlow(): Flow<List<CardInformationDto>> {
        return cardsWalletDao.getFlowCardsWallet().map { it.toDto() }
    }
}