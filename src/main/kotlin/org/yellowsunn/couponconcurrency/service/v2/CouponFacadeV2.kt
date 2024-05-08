package org.yellowsunn.couponconcurrency.service.v2

import org.springframework.stereotype.Component
import org.yellowsunn.couponconcurrency.repository.lock.LockRepository
import java.time.Duration

@Component
class CouponFacadeV2(
    private val couponServiceV2: CouponServiceV2,
    private val namedLockJdbcRepository: LockRepository,
) {
    fun giveCoupon(
        couponId: Long,
        userId: String,
    ) {
        namedLockJdbcRepository.executeWithLock("test:$userId:$couponId", Duration.ofSeconds(1)) {
            couponServiceV2.giveCoupon(couponId, userId)
        }
    }
}