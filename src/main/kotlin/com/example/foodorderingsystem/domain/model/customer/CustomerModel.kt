package com.example.foodorderingsystem.domain.model.customer

import com.example.foodorderingsystem.domain.model.user.UserModel
import java.util.UUID

class CustomerModel (
    val guid: UUID? = null,
    val user: UserModel? = null
)