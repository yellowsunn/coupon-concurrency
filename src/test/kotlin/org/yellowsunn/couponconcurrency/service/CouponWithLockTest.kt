package org.yellowsunn.couponconcurrency.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.yellowsunn.couponconcurrency.service.v1.CouponFacadeV1
import org.yellowsunn.couponconcurrency.service.v2.CouponFacadeV2
import org.yellowsunn.couponconcurrency.service.v3.CouponFacadeV3
import org.yellowsunn.couponconcurrency.service.v4.CouponFacadeV4
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class CouponWithLockTest {
    companion object {
        private const val COUPON_ID = 1L
        private const val USER_ID = "test user"
    }

    @Autowired lateinit var couponFacadeV1: CouponFacadeV1

    @Autowired lateinit var couponFacadeV2: CouponFacadeV2

    @Autowired lateinit var couponFacadeV3: CouponFacadeV3

    @Autowired lateinit var couponFacadeV4: CouponFacadeV4

    @Autowired lateinit var rollbackStrategies: CouponRollbackStrategies

    @AfterEach
    fun rollBack() {
        rollbackStrategies.clean(COUPON_ID, USER_ID)
    }

    @Test
    fun v1_namedLockTest() {
        // given
        val numberOfThread = 10

        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        // when
        executeWithLockTemplate(numberOfThread = numberOfThread, {
            couponFacadeV1.giveCoupon(COUPON_ID, USER_ID)
        }, logicSuccessCount = successCount, logicFailCount = failCount)

        // then
        assertAll(
            { Assertions.assertThat(successCount.get()).isEqualTo(1) },
            { Assertions.assertThat(failCount.get()).isEqualTo(numberOfThread - 1) },
        )
    }

    @Test
    fun v2_specificNamedLockTest() {
        // given
        val numberOfThread = 10

        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        // when
        executeWithLockTemplate(numberOfThread = numberOfThread, {
            couponFacadeV2.giveCoupon(COUPON_ID, USER_ID)
        }, logicSuccessCount = successCount, logicFailCount = failCount)

        // then
        assertAll(
            { Assertions.assertThat(successCount.get()).isEqualTo(1) },
            { Assertions.assertThat(failCount.get()).isEqualTo(numberOfThread - 1) },
        )
    }

    @Test
    fun v3_spinLockTest() {
        // given
        val numberOfThread = 10

        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        // when
        executeWithLockTemplate(numberOfThread = numberOfThread, {
            couponFacadeV3.giveCoupon(COUPON_ID, USER_ID)
        }, logicSuccessCount = successCount, logicFailCount = failCount)

        // then
        assertAll(
            { Assertions.assertThat(successCount.get()).isEqualTo(1) },
            { Assertions.assertThat(failCount.get()).isEqualTo(numberOfThread - 1) },
        )
    }

    @Test
    fun v4_distributedLockUsingRedissonTest() {
        // given
        val numberOfThread = 10

        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        // when
        executeWithLockTemplate(numberOfThread = numberOfThread, {
            couponFacadeV4.giveCoupon(COUPON_ID, USER_ID)
        }, logicSuccessCount = successCount, logicFailCount = failCount)

        // then
        assertAll(
            { Assertions.assertThat(successCount.get()).isEqualTo(1) },
            { Assertions.assertThat(failCount.get()).isEqualTo(numberOfThread - 1) },
        )
    }
}
