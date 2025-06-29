package com.example.foodorderingsystem.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "customers")
data class Customer (
    @Id
    @GeneratedValue
    val guid: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false)
    @JoinColumn(name = "user_guid")
    val user: User? = null
)