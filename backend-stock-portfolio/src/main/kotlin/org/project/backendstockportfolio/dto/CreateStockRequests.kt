package org.project.backendstockportfolio.dto

import org.project.backendstockportfolio.model.StockType

data class CreateStockRequests(
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val type: StockType
)