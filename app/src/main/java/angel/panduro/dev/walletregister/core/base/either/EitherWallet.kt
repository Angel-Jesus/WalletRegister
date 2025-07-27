package angel.panduro.dev.walletregister.core.base.either

import angel.panduro.dev.walletregister.core.base.error.Failure

sealed class EitherWallet<out L, out R> {
    data class Error<out L>(val value: L) : EitherWallet<L, Nothing>()
    data class Sucess<out R>(val value: R): EitherWallet<Nothing, R>()

    fun <T> either(
        fnL: (L) -> T,
        fnR: (R) -> T
    ): T = when(this) {
        is Error -> fnL(value)
        is Sucess -> fnR(value)
    }

    fun <T> mapSafely(fn: (R) -> T): EitherWallet<Failure, T>{
        return when(this){
            is Error -> Error(value as Failure)
            is Sucess -> {
                runCatching { fn(value) }
                    .fold(
                        onSuccess = { Sucess(it) },
                        onFailure = { Error(Failure.MapperFailure(it as Exception)) }
                    )
            }
        }
    }
}