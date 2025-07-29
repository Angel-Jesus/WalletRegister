package angel.panduro.dev.walletregister.core.base.usecase

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in Params, out Type> : UseCase<Params, Type> {

    protected abstract suspend fun run(params: Params): EitherWallet<Failure, Type>

    override fun execute(params: Params): Flow<EitherWallet<Failure, Type>> = flow {
        val result = runCatching { run(params) }.getOrElse { EitherWallet.Error(Failure.UnkownFailure(it.message)) }
        emit(result)
    }.catch { e -> emit(EitherWallet.Error(Failure.UnkownFailure(e.message))) }
}