package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.RestaurantDTO
import com.example.foodorderingsystem.mapper.RestaurantMapper
import com.example.foodorderingsystem.repository.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantAppService (
    private val restaurantRepository: RestaurantRepository,
    private val restaurantMapper: RestaurantMapper
) {
    fun getRestaurants(): List<RestaurantDTO> {
        val restaurants = restaurantRepository.findAll()

        return restaurantMapper.toListDTO(restaurants)
    }
}