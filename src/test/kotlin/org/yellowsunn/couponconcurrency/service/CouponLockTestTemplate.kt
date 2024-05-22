package org.yellowsunn.couponconcurrency.service

import org.apache.logging.log4j.util.Supplier
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

fun <T> executeWithLockTemplate(
    numberOfThread: Int = 10,
    businessLogic: Supplier<T>,
    logicSuccessCount: AtomicInteger = AtomicInteger(0),
    logicFailCount: AtomicInteger = AtomicInteger(0),
) {
    val countDownLatch = CountDownLatch(numberOfThread)
    val executorService = Executors.newFixedThreadPool(numberOfThread)

    for (thread in 1..numberOfThread) {
        executorService.execute {
            try {
                businessLogic.get()
                logicSuccessCount.getAndIncrement()
            } catch (e: RuntimeException) {
                logicFailCount.getAndIncrement()
            } finally {
                countDownLatch.countDown()
            }
        }
    }

    countDownLatch.await()
    executorService.shutdown()
}
