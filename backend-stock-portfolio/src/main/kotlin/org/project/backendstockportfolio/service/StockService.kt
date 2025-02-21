package org.project.backendstockportfolio.service

import org.project.backendstockportfolio.dto.CreateStockRequests
import org.project.backendstockportfolio.model.CommonStock
import org.project.backendstockportfolio.model.PreferredStock
import org.project.backendstockportfolio.model.Stock
import org.project.backendstockportfolio.model.StockType
import org.project.backendstockportfolio.repository.StockRepository
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository
) {

    fun findBySymbol(stockSymbol: String): Stock {
        return stockRepository.findBySymbol(stockSymbol) ?: throw Exception("Stock not found")
    }

    fun addStock(request: CreateStockRequests) {
        //Create stock depending on the type
        val stock: Stock = when (request.type) {
            StockType.COMMON -> CommonStock(name = request.name, symbol = request.symbol, currentPrice = request.currentPrice)
            StockType.PREFERRED -> PreferredStock(name = request.name, symbol = request.symbol, currentPrice = request.currentPrice)
            else -> throw IllegalArgumentException("Invalid stock type: ${request.type}")
        }

        stockRepository.insert(stock)
    }

    fun findAll(): List<Stock> {
        return stockRepository.findAll()
    }
}