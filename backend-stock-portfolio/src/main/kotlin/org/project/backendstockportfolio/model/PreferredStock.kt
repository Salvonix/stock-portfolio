package org.project.backendstockportfolio.model

import kotlin.random.Random

//Less volatile Stock type
data class PreferredStock(
    override val name: String,
    override val symbol: String,
    override val currentPrice: Double
) : Stock(name = name, symbol = symbol, currentPrice = currentPrice) {

    override fun interestOnPrice(): Stock {
        val priceChange = Random.nextDouble(-1.5, 1.5)  // Lower volatility
        val newPrice = (this.currentPrice + priceChange).coerceAtLeast(0.5)
        return PreferredStock(
            currentPrice = newPrice,
            name = this.name,
            symbol = this.symbol
        )
    }
}
