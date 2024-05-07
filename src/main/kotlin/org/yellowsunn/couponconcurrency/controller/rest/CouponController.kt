package org.yellowsunn.couponconcurrency.controller.rest

import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.yellowsunn.couponconcurrency.service.CouponFacade

@RestController
class CouponController(
    private val couponFacade: CouponFacade,
) {
    @PostMapping("/api/coupons/{couponId}")
    fun giveCoupon(
        @CookieValue(value = "userId", required = true) userId: String,
        @PathVariable couponId: Long,
    ) {
        return couponFacade.giveCoupon(couponId = couponId, userId = userId)
    }
}
