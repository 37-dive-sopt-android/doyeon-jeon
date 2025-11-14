package com.sopt.dive.core.util

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.ensureActive
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext

suspend fun <R> suspendRunCatching(block: suspend () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (h: HttpException) {
        Result.failure(h)
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        coroutineContext.ensureActive()
        Result.failure(e)
    }
}
