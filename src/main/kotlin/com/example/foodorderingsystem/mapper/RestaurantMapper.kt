package com.example.foodorderingsystem.mapper

import com.example.foodorderingsystem.dto.RestaurantDTO
import com.example.foodorderingsystem.entity.Restaurant
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class RestaurantMapper {
    fun toDTO(restaurant: Restaurant): RestaurantDTO {
        return RestaurantDTO(
            guid = restaurant.guid ?: throw IllegalArgumentException("GUID do restaurante é nulo"),
            name = restaurant.name,
            cnpj = restaurant.cnpj,
            createdAt = restaurant.createdAt
                ?.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            updatedAt = restaurant.updatedAt?.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
        )
    }

    fun toListDTO(restaurants: List<Restaurant>): List<RestaurantDTO> {
        return restaurants.map { restaurant ->
            RestaurantDTO(
                guid = restaurant.guid ?: throw IllegalArgumentException("GUID do restaurante é nulo"),
                name = restaurant.name,
                cnpj = restaurant.cnpj,
                createdAt = restaurant.createdAt
                    ?.toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                updatedAt = restaurant.updatedAt?.toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            )
        }
    }
}