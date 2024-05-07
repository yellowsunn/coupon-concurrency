package org.yellowsunn.couponconcurrency.domain

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseTimeEntity {
    lateinit var createdAt: ZonedDateTime
        private set

    lateinit var modifiedAt: ZonedDateTime
        private set

    @PrePersist
    fun prePersist() {
        val now = ZonedDateTime.now()
        this.createdAt = now
        this.modifiedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        this.modifiedAt = ZonedDateTime.now()
    }
}