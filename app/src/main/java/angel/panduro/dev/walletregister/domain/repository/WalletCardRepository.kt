package angel.panduro.dev.walletregister.domain.repository

import angel.panduro.dev.walletregister.data.dto.CardInformationDto
import kotlinx.coroutines.flow.Flow

interface WalletCardRepository {
    suspend fun saveCard(cardInformation: CardInformationDto)
    suspend fun deleteCard(idCard: Long)
    suspend fun getCard(idCard: Long): CardInformationDto?
    suspend fun getAllCards(): List<CardInformationDto>
    fun getAllCardsFlow(): Flow<List<CardInformationDto>>

}