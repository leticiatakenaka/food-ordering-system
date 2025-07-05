package com.example.foodorderingsystem.interfaces.controller

import com.example.foodorderingsystem.application.dto.order.ItemDTO
import com.example.foodorderingsystem.application.dto.restaurant.RestaurantDTO
import com.example.foodorderingsystem.application.service.RestaurantAppService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantAppService: RestaurantAppService,
) {
    @GetMapping()
    fun getRestaurants(
    ): ResponseEntity<List<RestaurantDTO>> {
        val order = restaurantAppService.getRestaurants()
        return ResponseEntity.status(HttpStatus.OK).body(order)
    }

    @GetMapping("/{restaurantGuid}/items")
    fun getItemsByRestaurant(@PathVariable restaurantGuid: UUID): ResponseEntity<List<ItemDTO>> {
        val items = restaurantAppService.getItemsByRestaurant(restaurantGuid)
        return ResponseEntity.ok(items)
    }
}
