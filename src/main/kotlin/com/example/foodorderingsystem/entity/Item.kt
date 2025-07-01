package com.example.foodorderingsystem.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "items")
data class Item (
    @Id
    @GeneratedValue
    val guid: UUID,

    val name: String,

    val price: BigDecimal,

    @Column(name = "stock_quantity")
    val stockQuantity: Int,

    @Column(name = "created_at")
    val createdAt: Date,

    @Column(name = "updated_at")
    val updatedAt: Date,

    @ManyToOne
    @JoinColumn(name = "restaurant_guid", nullable = false)
    val restaurant: Restaurant,

    @ManyToOne
    @JoinColumn(name = "food_type_guid", nullable = false)
    val foodType: FoodType
)