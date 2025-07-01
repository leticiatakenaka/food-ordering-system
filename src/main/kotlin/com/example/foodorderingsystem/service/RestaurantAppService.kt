package com.example.foodorderingsystem.service

import com.example.foodorderingsystem.dto.*
import com.example.foodorderingsystem.exception.NotFoundException
import com.example.foodorderingsystem.mapper.*
import com.example.foodorderingsystem.repository.ItemRepository
import com.example.foodorderingsystem.repository.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RestaurantAppService (
    private val restaurantRepository: RestaurantRepository,
    private val restaurantMapper: RestaurantMapper,
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper
) {
    fun getRestaurants(): List<RestaurantDTO> {
        val restaurants = restaurantRepository.findAll()

        return restaurantMapper.toListDTO(restaurants)
    }

    fun getItemsByRestaurant(restaurantGuid: UUID): List<ItemDTO> {
        if (!restaurantRepository.existsById(restaurantGuid)) {
            throw NotFoundException("Restaurante não encontrado")
        }

        val items = itemRepository.findByRestaurantGuid(restaurantGuid)
        return itemMapper.toListDTO(items)
    }
}