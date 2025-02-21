package org.project.backendstockportfolio.model

import kotlin.random.Random

//More Volatile Stock type
data class CommonStock(
    override val name: String,
    override val symbol: String,
    override val currentPrice: Double
) : Stock(name = name, symbol = symbol, currentPrice = currentPrice) {

    override fun interestOnPrice(): Stock {
        val priceChange = Random.nextDouble(-5.0, 5.0)  // Higher volatility
        val newPrice = (this.currentPrice + priceChange).coerceAtLeast(0.5)
        return CommonStock(
            currentPrice = newPrice,
            name = this.name,
            symbol = this.symbol
        )
    }
}
