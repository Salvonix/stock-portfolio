package org.project.backendstockportfolio.service

import org.project.backendstockportfolio.repository.StockRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class StockPriceUpdater(private val stockRepository: StockRepository) {

    @Scheduled(fixedRate = 10000)
    fun updateStockPrices() {
        val stocks = stockRepository.findAll()

        stocks.forEach { stock ->
            val priceChange = Random.nextDouble(-1.5, 1.5)
            val newPrice = (stock.currentPrice + priceChange).coerceAtLeast(0.5)

            val updatedStock = stock.copy(currentPrice = newPrice)
            stockRepository.save(updatedStock)

            println("Updated ${stock.symbol} price: $${"%.2f".format(newPrice)}")
        }
    }
}
