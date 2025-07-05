package com.example.foodorderingsystem.infrastructure.persistence.entity
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "order_items")
data class OrderItemEntity(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "order_guid")
    @JsonBackReference
    val order: OrderEntity,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "item_guid")
    var item: ItemEntity? = null,

    val quantity: Int = 1,

    val price: BigDecimal,
)
