package angel.panduro.dev.walletregister.core.base.either

import angel.panduro.dev.walletregister.core.base.error.Failure

sealed class EitherWallet<out L, out R> {
    data class Error<out L>(val value: L) : EitherWallet<L, Nothing>()
    data class Success<out R>(val value: R): EitherWallet<Nothing, R>()

    fun <T> either(
        fnL: (L) -> T,
        fnR: (R) -> T
    ): T = when(this) {
        is Error -> fnL(value)
        is Success -> fnR(value)
    }

    fun <T> mapSafely(fn: (R) -> T): EitherWallet<Failure, T>{
        return when(this){
            is Error -> Error(value as Failure)
            is Success -> {
                runCatching { fn(value) }
                    .fold(
                        onSuccess = { Success(it) },
                        onFailure = { Error(Failure.MapperFailure(it as Exception)) }
                    )
            }
        }
    }
}

inline fun <L, R> EitherWallet<L, R>.onSuccess(block: (R) -> Unit): EitherWallet<L, R> {
    if (this is EitherWallet.Success) block(value)
    return this
}

inline fun <L, R> EitherWallet<L, R>.onError(block: (L) -> Unit): EitherWallet<L, R> {
    if (this is EitherWallet.Error) block(value)
    return this
}