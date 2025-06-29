package com.example.foodorderingsystem.entity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "order_guid")
    val order: Order,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "item_guid")
    val item: Item,

    val quantity: Int = 1,

    val price: Double = 0.0,
)
