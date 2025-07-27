package angel.panduro.dev.walletregister.core.base.usecase

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

abstract class BaseFlowUseCase<in Params, out Type> : UseCase<Params, Type> {
    protected abstract fun run(params: Params): Flow<EitherWallet<Failure, Type>>

    override fun execute(params: Params): Flow<EitherWallet<Failure, Type>> = try {
        run(params)
    } catch (e: Exception) {
        flowOf(EitherWallet.Error(Failure.fromThrowable(e)))
    }
}