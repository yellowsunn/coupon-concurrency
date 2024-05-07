package org.yellowsunn.couponconcurrency.controller.rest

import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.yellowsunn.couponconcurrency.service.CouponService

@RestController
class CouponController(
    private val couponService: CouponService,
) {
    @PostMapping("/api/coupons/{couponId}")
    fun giveCoupon(
        @CookieValue(value = "userId", required = true) userId: String,
        @PathVariable couponId: Long,
    ) {
        return couponService.giveCoupon(couponId = couponId, userId = userId)
    }
}
