package com.example.foodorderingsystem.infrastructure.persistence.repository

import com.example.foodorderingsystem.infrastructure.persistence.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SpringDataOrderRepository : JpaRepository<OrderEntity, UUID> {
    @Query("""
        SELECT o FROM OrderEntity o 
        LEFT JOIN FETCH o.items i 
        LEFT JOIN FETCH i.item 
        WHERE o.guid = :guid
    """)
    fun findWithItemsByGuid(@Param("guid") guid: UUID): OrderEntity?
}