package angel.panduro.dev.walletregister.core.base.repository

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRoom {
    protected suspend inline fun <reified T> safeBaseRoom(crossinline useCaseBlock: suspend () -> T): EitherWallet<Failure, T> {
        return try {
            withContext(Dispatchers.IO){
                val result = useCaseBlock.invoke()
                EitherWallet.Success(result)
            }
        }catch (e: Exception){
            EitherWallet.Error(Failure.UnkownFailure(e.message))
        }
    }
}