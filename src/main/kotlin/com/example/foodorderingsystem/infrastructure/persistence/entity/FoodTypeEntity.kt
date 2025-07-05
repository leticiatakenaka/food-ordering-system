package com.example.foodorderingsystem.infrastructure.persistence.entity

import com.example.foodorderingsystem.domain.model.restaurant.enums.FoodType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "food_types")
data class FoodTypeEntity(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @Enumerated(EnumType.STRING)
    val description: FoodType
)
