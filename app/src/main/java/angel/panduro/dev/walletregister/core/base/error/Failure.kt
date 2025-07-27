package angel.panduro.dev.walletregister.core.base.error

sealed class Failure {
    data class DatabaseFailure(val message: String?): Failure()
    data class MapperFailure(val exception: Exception?): Failure()
    data class UnkownFailure(val message: String?): Failure()

    companion object{
        fun fromThrowable(throwable: Throwable): Failure = when(throwable){
            else -> UnkownFailure(throwable.message)
        }
    }
}