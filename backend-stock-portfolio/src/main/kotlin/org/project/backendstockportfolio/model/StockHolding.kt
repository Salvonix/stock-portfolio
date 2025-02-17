package org.project.backendstockportfolio.model

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

data class StockHolding(
    @DBRef(lazy = false) var stock: Stock,  // Ensure the stock is resolved eagerly
    val sharesOwned: Int,
    val purchasePrice: Double
) {
    // These fields are based on the current stock price, so they will always reflect the updated price
    val totalValue: Double
        get() = sharesOwned * stock.currentPrice

    val purchaseValue: Double
        get() = purchasePrice * sharesOwned  // Assuming purchase price is per share

    val profitValue: Double
        get() = totalValue - purchaseValue
}
