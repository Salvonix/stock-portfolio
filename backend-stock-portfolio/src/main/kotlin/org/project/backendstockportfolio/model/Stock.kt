package org.project.backendstockportfolio.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "stocks")
data class Stock(
    @Id val id: String? = null,
    val name: String,
    val symbol: String,
    val currentPrice: Double
) {
    // Method to update the stock price if needed (in case of external updates)
    fun updatePrice(newPrice: Double): Stock {
        return this.copy(currentPrice = newPrice)
    }
}
