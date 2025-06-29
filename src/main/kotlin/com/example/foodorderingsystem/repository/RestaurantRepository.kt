package com.example.foodorderingsystem.repository

import com.example.foodorderingsystem.entity.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RestaurantRepository : JpaRepository<Restaurant, UUID>{
}