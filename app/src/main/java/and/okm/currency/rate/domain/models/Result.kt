package and.okm.currency.rate.domain.models

import and.okm.currency.rate.domain.models.Result.Failure
import and.okm.currency.rate.domain.models.Result.Success

sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure(val cause: Throwable) : Result<Nothing>()
}

inline fun <T> Result(block: () -> T): Result<T> = try {
    Success(block())
} catch (t: Throwable) {
    Failure(t)
}
