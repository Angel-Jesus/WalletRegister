package angel.panduro.dev.walletregister.core.base.usecase

interface UseCase<in P, out R> {
    suspend fun execute(params: P): R
}