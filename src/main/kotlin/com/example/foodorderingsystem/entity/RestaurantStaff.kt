package com.example.foodorderingsystem.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "restaurant_staff")
data class RestaurantStaff (
    @Id
    @GeneratedValue
    val guid: UUID,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "role_guid")
    val role: Role,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "user_guid")
    val user: User,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "restaurant_guid")
    val restaurant: Restaurant
)