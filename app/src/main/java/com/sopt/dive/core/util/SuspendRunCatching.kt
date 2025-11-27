package com.sopt.dive.core.util

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <R> apiRunCatching(block: suspend () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (h: HttpException) {
        Result.failure(h)
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        currentCoroutineContext().ensureActive()
        Result.failure(e)
    }
}
