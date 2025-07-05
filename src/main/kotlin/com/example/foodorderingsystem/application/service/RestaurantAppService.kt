package com.example.foodorderingsystem.application.service

import com.example.foodorderingsystem.application.dto.order.ItemDTO
import com.example.foodorderingsystem.application.dto.restaurant.RestaurantDTO
import com.example.foodorderingsystem.application.mapper.ItemMapper
import com.example.foodorderingsystem.application.mapper.RestaurantMapper
import com.example.foodorderingsystem.interfaces.exception.NotFoundException
import com.example.foodorderingsystem.domain.repository.ItemRepository
import com.example.foodorderingsystem.domain.repository.RestaurantRepository
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