package com.example.foodorderingsystem.controller

import com.example.foodorderingsystem.dto.RestaurantDTO
import com.example.foodorderingsystem.service.RestaurantAppService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
