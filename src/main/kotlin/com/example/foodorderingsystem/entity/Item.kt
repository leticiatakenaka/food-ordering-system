package com.example.foodorderingsystem.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "items")
data class Item (
    @Id
    @GeneratedValue
    val guid: UUID,

    val name: String,

    val price: Double,

    @Column(name = "stock_quantity")
    val stockQuantity: Int,

    @Column(name = "created_at")
    val createdAt: Date,

    @Column(name = "updated_at")
    val updatedAt: Date,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "restaurant_guid")
    val restaurant: Restaurant,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "food_type_guid")
    val foodType: FoodType,
)