package org.project.backendstockportfolio.service

import org.project.backendstockportfolio.model.Stock
import org.project.backendstockportfolio.repository.StockRepository
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    fun findBySymbol(stockSymbol: String): Stock {
        return stockRepository.findBySymbol(stockSymbol) ?: throw Exception("Stock not found")
    }

    fun addStock(stock: Stock) {
        stockRepository.insert(stock)
    }

    fun findAll(): List<Stock> {
        return stockRepository.findAll()
    }
}