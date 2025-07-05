package com.example.foodorderingsystem.application.mapper

import com.example.foodorderingsystem.application.dto.restaurant.RestaurantDTO
import com.example.foodorderingsystem.infrastructure.persistence.entity.RestaurantEntity
import com.example.foodorderingsystem.interfaces.exception.BadRequestException
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class RestaurantMapper {
    fun toDTO(restaurant: RestaurantEntity): RestaurantDTO {
        return RestaurantDTO(
            guid = restaurant.guid ?: throw BadRequestException("GUID do restaurante é nulo"),
            name = restaurant.name,
            cnpj = restaurant.cnpj,
            createdAt = restaurant.createdAt
                .toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            updatedAt = restaurant.updatedAt.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
        )
    }

    fun toListDTO(restaurants: List<RestaurantEntity>): List<RestaurantDTO> {
        return restaurants.map { restaurant ->
            RestaurantDTO(
                guid = restaurant.guid ?: throw BadRequestException("GUID do restaurante é nulo"),
                name = restaurant.name,
                cnpj = restaurant.cnpj,
                createdAt = restaurant.createdAt
                    .toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                updatedAt = restaurant.updatedAt.toInstant()
                    ?.atZone(ZoneId.systemDefault())
                    ?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            )
        }
    }
}