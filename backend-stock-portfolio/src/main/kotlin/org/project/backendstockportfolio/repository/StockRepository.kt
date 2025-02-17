package org.project.backendstockportfolio.repository

import org.project.backendstockportfolio.model.Stock
import org.springframework.data.mongodb.repository.MongoRepository

interface StockRepository : MongoRepository<Stock, String> {
    fun findBySymbol(symbol: String): Stock?
}