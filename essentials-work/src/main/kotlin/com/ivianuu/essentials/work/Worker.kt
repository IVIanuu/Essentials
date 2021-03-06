package com.ivianuu.essentials.work

import androidx.work.*
import com.ivianuu.injekt.*
import java.util.*
import kotlin.time.*

typealias Worker<I> = suspend WorkScope.() -> ListenableWorker.Result

interface WorkScope {
  val inputData: Data
  val id: UUID
  val tags: Set<String>
  val runAttemptCount: Int
  suspend fun setProgress(data: Data)
  suspend fun setForeground(foregroundInfo: ForegroundInfo)
}

abstract class WorkerId(val value: String)

typealias WorkerElement = Pair<WorkerId, () -> Worker<*>>

@Provide fun <@Spread T : Worker<I>, I : WorkerId> workerElement(
  id: I,
  factory: () -> T
): WorkerElement = id to factory

fun WorkerId.toFunctionalWorkerTag() = WORKER_ID_TAG_PREFIX + value

fun OneTimeWorkRequestBuilder(id: WorkerId): OneTimeWorkRequest.Builder =
  OneTimeWorkRequestBuilder<FunctionalWorker>()
    .addTag(id.toFunctionalWorkerTag())

fun PeriodicWorkRequestBuilder(
  id: WorkerId,
  repeatInterval: Duration,
  flexTimeInterval: Duration? = null
): PeriodicWorkRequest.Builder =
  (if (flexTimeInterval != null) PeriodicWorkRequestBuilder<FunctionalWorker>(
    repeatInterval.toJavaDuration(), flexTimeInterval.toJavaDuration()
  ) else PeriodicWorkRequestBuilder<FunctionalWorker>(repeatInterval.toJavaDuration()))
    .addTag(id.toFunctionalWorkerTag())

internal const val WORKER_ID_TAG_PREFIX = "worker_id_"
