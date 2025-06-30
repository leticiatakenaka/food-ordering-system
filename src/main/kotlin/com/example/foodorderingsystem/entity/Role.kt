package com.example.foodorderingsystem.entity

import com.example.foodorderingsystem.enums.Role
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue
    val guid: UUID,

    @Enumerated(EnumType.STRING)
    val description: Role
)
