package angel.panduro.dev.walletregister.core.base.usecase

abstract class BaseUseCase<in Params, out Type> : UseCase<Params, Type> {

    protected abstract suspend fun run(params: Params): Type
    override suspend fun execute(params: Params): Type = run(params)
}