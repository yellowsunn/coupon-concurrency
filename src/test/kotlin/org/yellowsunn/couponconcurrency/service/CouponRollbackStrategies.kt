package org.yellowsunn.couponconcurrency.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.yellowsunn.couponconcurrency.repository.persistence.jpa.UserCouponJpaRepository

@Component
class CouponRollbackStrategies(
    private val userCouponJpaRepository: UserCouponJpaRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) {
    companion object {
        private const val COUPON_TOTAL_COUNT_PREFIX = "coupon"
        private const val LOCK_KEY_PREFIX = "test"
    }

    fun clean(
        couponId: Long,
        userId: String,
    ) {
        userCouponJpaRepository.deleteAll()

        evictRedisLock(couponId, userId)
        evictRedisCouponTotalCount(couponId)
    }

    private fun evictRedisLock(
        couponId: Long,
        userId: String,
    ) {
        redisTemplate.delete(getRedisLockNames(couponId, userId))
    }

    private fun evictRedisCouponTotalCount(couponId: Long) {
        redisTemplate.delete(getRedisTotalCountName(couponId))
    }

    private fun getRedisLockNames(
        couponId: Long,
        userId: String,
    ): List<String> {
        return listOf(LOCK_KEY_PREFIX, "${LOCK_KEY_PREFIX}:$userId:$couponId")
    }

    private fun getRedisTotalCountName(couponId: Long): String {
        return "$COUPON_TOTAL_COUNT_PREFIX:$couponId"
    }
}
