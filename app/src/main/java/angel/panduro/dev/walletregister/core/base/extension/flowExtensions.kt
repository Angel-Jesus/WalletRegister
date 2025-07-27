package angel.panduro.dev.walletregister.core.base.extension

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<EitherWallet<Failure, T>>.collectEither(
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (Failure) -> Unit = {}
){
    this.collect { result ->
        when(result){
            is EitherWallet.Error -> onError(result.value)
            is EitherWallet.Sucess -> onSuccess(result.value)
        }
    }
}