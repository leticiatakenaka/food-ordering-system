package com.example.foodorderingsystem.entity
import jakarta.persistence.*

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val itemName: String,

    val quantity: Int = 1,

    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Order
)
