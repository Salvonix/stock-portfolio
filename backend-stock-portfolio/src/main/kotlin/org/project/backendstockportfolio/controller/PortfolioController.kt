package org.project.backendstockportfolio.controller

import org.project.backendstockportfolio.model.Portfolio
import org.project.backendstockportfolio.service.PortfolioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/portfolios")
class PortfolioController(@Autowired val portfolioService: PortfolioService) {

    @PostMapping("/{portfolioId}/stocks")
    fun addStock(
        @PathVariable portfolioId: String,
        @RequestParam stockSymbol: String,
        @RequestParam shares: Int
    ): Portfolio {
        return portfolioService.addStockToPortfolio(portfolioId, stockSymbol, shares)
    }

    @GetMapping("/{owner}")
    fun getPortfolioByOwner(@PathVariable owner: String): Portfolio {
        return portfolioService.findByOwner(owner)
    }

    @PostMapping("/{owner}")
    fun createPortfolio(@PathVariable owner: String): Portfolio {
        return portfolioService.createPortfolio(owner)
    }

    @PostMapping("/{portfolioId}/stocks/price")
    fun updateAllStockPricesInPortfolio(
        @PathVariable portfolioId: String
    ): Portfolio {
        return portfolioService.updateAllStockPricesInPortfolio(portfolioId)
    }
}
