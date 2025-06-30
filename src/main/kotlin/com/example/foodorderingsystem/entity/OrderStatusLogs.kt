package com.example.foodorderingsystem.entity

import com.example.foodorderingsystem.enums.OrderStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "order_status_logs")
data class OrderStatusLogs (
    @Id
    @GeneratedValue
    val guid: UUID,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "order_guid")
    val order: Order,

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    var oldStatus: OrderStatus,

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    var newStatus: OrderStatus,

    @Column(name = "changed_at")
    var changedAt: Date? = null,
)