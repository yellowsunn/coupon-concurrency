package org.yellowsunn.couponconcurrency.repository.lock.local

import org.apache.logging.log4j.util.Supplier
import org.springframework.stereotype.Repository
import org.yellowsunn.couponconcurrency.repository.lock.LockRepository
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

@Repository
class LocalLockRepository : LockRepository {
    companion object {
        private const val FAIL_TO_ACQUIRE_LOCK_EXCEPTION_MESSAGE = "LOCK을 획득하지 못했습니다."
    }

    private val lock = ReentrantLock()

    override fun <T> executeWithLock(
        lockName: String,
        timeout: Duration,
        supplier: Supplier<T>,
    ): T {
        try {
            val acquired = lock.tryLock(timeout.toSeconds(), TimeUnit.SECONDS)

            if (!acquired) {
                throw RuntimeException(FAIL_TO_ACQUIRE_LOCK_EXCEPTION_MESSAGE)
            }

            return supplier.get()
        } catch (e: InterruptedException) {
            throw RuntimeException(e.message, e)
        } finally {
            // lock 획득 못하고 unlock 호출 시에 exception 발생하므로, 방어 로직 작성
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }
}
