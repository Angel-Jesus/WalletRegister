package angel.panduro.dev.walletregister.core.base.usecase

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import kotlinx.coroutines.flow.Flow

interface UseCase<in P, out R> {
    fun execute(params: P): Flow<EitherWallet<Failure, R>>
}