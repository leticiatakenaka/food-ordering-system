package com.example.foodorderingsystem.domain.repository

import com.example.foodorderingsystem.infrastructure.persistence.entity.RestaurantEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RestaurantRepository : JpaRepository<RestaurantEntity, UUID>{
}