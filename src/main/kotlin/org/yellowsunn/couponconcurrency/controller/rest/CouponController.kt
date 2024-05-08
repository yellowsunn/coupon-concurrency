package org.yellowsunn.couponconcurrency.controller.rest

import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.yellowsunn.couponconcurrency.service.CouponFacadeV1
import org.yellowsunn.couponconcurrency.service.CouponFacadeV2

@RestController
class CouponController(
    private val couponFacadeV1: CouponFacadeV1,
    private val couponFacadeV2: CouponFacadeV2,
) {
    @PostMapping("/api/v1/coupons/{couponId}")
    fun giveCoupon(
        @CookieValue(value = "userId", required = true) userId: String,
        @PathVariable couponId: Long,
    ) {
        return couponFacadeV1.giveCoupon(couponId = couponId, userId = userId)
    }

    @PostMapping("/api/v2/coupons/{couponId}")
    fun giveCouponV2(
        @CookieValue(value = "userId", required = true) userId: String,
        @PathVariable couponId: Long,
    ) {
        return couponFacadeV2.giveCoupon(couponId = couponId, userId = userId)
    }
}
