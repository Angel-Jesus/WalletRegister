package angel.panduro.dev.walletregister.core.base.usecase

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in Params, out Type> : UseCase<Params, Type> {

    protected abstract suspend fun run(params: Params): EitherWallet<Failure, Type>

    override fun execute(params: Params): Flow<EitherWallet<Failure, Type>> = flow {
        val result = runCatching { run(params) }.getOrElse { EitherWallet.Error(Failure.fromThrowable(it)) }
        emit(result)
    }.catch { e -> emit(EitherWallet.Error(Failure.fromThrowable(e))) }.flowOn(Dispatchers.IO)
}