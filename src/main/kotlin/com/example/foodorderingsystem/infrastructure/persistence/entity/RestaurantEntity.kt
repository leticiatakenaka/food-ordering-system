package com.example.foodorderingsystem.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "restaurants")
data class RestaurantEntity(
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    val name: String = "",

    val cnpj: String = "",

    @Column(name = "created_at")
    val createdAt: Date = Date(),

    @Column(name = "updated_at")
    val updatedAt: Date = Date(),

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    val items: List<ItemEntity> = emptyList()
)
