package com.example.foodorderingsystem.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "users")
data class UserEntity (
    @Id
    @GeneratedValue
    val guid: UUID,

    val name: String,

    val email: String,

    val cpf: String,

    @Column(name = "created_at")
    val createdAt: Date,

    @Column(name = "updated_at")
    val updatedAt: Date,
)