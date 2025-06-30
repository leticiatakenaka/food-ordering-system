package com.example.foodorderingsystem.entity

import com.example.foodorderingsystem.enums.FoodType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "food_types")
data class FoodType(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @Enumerated(EnumType.STRING)
    val description: FoodType
)
