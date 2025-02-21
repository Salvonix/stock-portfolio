package org.project.backendstockportfolio.service

import org.project.backendstockportfolio.repository.StockRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class StockPriceUpdater(private val stockRepository: StockRepository) {
    //Run this method at every 10s interval
    @Scheduled(fixedRate = 10000)
    fun updateStockPrices() {
        val stocks = stockRepository.findAll()

        stocks.forEach { stock ->
            //Set new price depending on the type of the stock
            stockRepository.save(stock.interestOnPrice())

            println("Updated ${stock.symbol} price: $${"%.2f".format(stock.currentPrice)}")
        }
    }
}
