package org.yellowsunn.couponconcurrency.service.v0

import org.springframework.stereotype.Component
import org.yellowsunn.couponconcurrency.repository.lock.LockRepository
import java.time.Duration

/**
 * Local Lock (ReentrantLock)
 */
@Component
class CouponFacadeV0(
    private val couponServiceV0: CouponServiceV0,
    private val localLockRepository: LockRepository,
) {
    fun giveCoupon(
        couponId: Long,
        userId: String,
    ) {
        localLockRepository.executeWithLock("test:$couponId:$userId", Duration.ofSeconds(1)) {
            couponServiceV0.giveCoupon(couponId, userId)
        }
    }
}
