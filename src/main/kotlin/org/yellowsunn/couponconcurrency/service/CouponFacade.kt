package org.yellowsunn.couponconcurrency.service

import org.springframework.stereotype.Component
import org.yellowsunn.couponconcurrency.repository.persistence.LockRepository
import java.time.Duration

@Component
class CouponFacade(
    private val couponService: CouponService,
    private val namedLockJdbcRepository: LockRepository,
) {
    fun giveCoupon(
        couponId: Long,
        userId: String,
    ) {
        namedLockJdbcRepository.executeWithLock("test", Duration.ofSeconds(1)) {
            couponService.giveCoupon(couponId, userId)
        }
    }
}
