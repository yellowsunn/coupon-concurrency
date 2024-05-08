package org.yellowsunn.couponconcurrency.service

import org.springframework.stereotype.Component
import org.yellowsunn.couponconcurrency.repository.persistence.LockRepository
import java.time.Duration

@Component
class CouponFacadeV1(
    private val couponServiceV1: CouponServiceV1,
    private val namedLockJdbcRepository: LockRepository,
) {
    fun giveCoupon(
        couponId: Long,
        userId: String,
    ) {
        namedLockJdbcRepository.executeWithLock("test", Duration.ofSeconds(1)) {
            couponServiceV1.giveCoupon(couponId, userId)
        }
    }
}
