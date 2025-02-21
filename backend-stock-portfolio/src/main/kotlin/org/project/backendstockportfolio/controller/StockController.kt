package org.project.backendstockportfolio.controller

import org.project.backendstockportfolio.dto.CreateStockRequests
import org.project.backendstockportfolio.model.Stock
import org.project.backendstockportfolio.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stocks")
class StockController(@Autowired val stockService: StockService) {
    @GetMapping("/{symbol}")
    fun stock(
        @PathVariable symbol: String) : Stock {
        return stockService.findBySymbol(symbol)
    }

    @GetMapping()
    fun stocks() : List<Stock> {
        return stockService.findAll()
    }
    @PostMapping("/add")
    fun addStock(@RequestBody stockRequests: CreateStockRequests) {
        stockService.addStock(stockRequests)
    }
}