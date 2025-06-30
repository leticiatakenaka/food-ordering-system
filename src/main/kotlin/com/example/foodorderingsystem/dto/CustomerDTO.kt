package com.example.foodorderingsystem.dto

import java.util.UUID

data class CustomerDTO (
    val guid: UUID? = null,
    val name: String
    )