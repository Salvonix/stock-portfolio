package org.project.backendstockportfolio.service

import org.project.backendstockportfolio.model.Portfolio
import org.project.backendstockportfolio.model.StockHolding
import org.project.backendstockportfolio.repository.PortfolioRepository
import org.project.backendstockportfolio.repository.StockRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class PortfolioService(
    @Autowired val portfolioRepository: PortfolioRepository,
    @Autowired  val stockRepository: StockRepository
) {
    fun createPortfolio(owner: String): Portfolio {
        val portfolio = Portfolio(owner = owner)
        return portfolioRepository.insert(portfolio)
    }

    fun addStockToPortfolio(portfolioId: String, stockSymbol: String, shares: Int): Portfolio {
        val portfolio = portfolioRepository.findById(portfolioId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found") }
        val stock = stockRepository.findBySymbol(stockSymbol) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"Stock not found")

        val newHolding = StockHolding(stock, shares, stock.currentPrice)
        val updatedPortfolio = portfolio.copy(holdings = portfolio.holdings + newHolding)

        return portfolioRepository.save(updatedPortfolio)
    }

    fun findByOwner(owner: String): Portfolio {
        return portfolioRepository.findByOwner(owner) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found")
    }

    @Transactional
    fun updateAllStockPricesInPortfolio(portfolioId: String): Portfolio {
        // Fetch the portfolio
        val portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow { Exception("Portfolio not found") }

        // Loop through each holding and update the stock price
        portfolio.holdings.forEach { stockHolding ->
            val stockSymbol = stockHolding.stock.symbol
            val currentPrice = stockRepository.findBySymbol(stockSymbol)?.currentPrice ?: 0.0

            // Update the stock price in the StockHolding object
            stockHolding.stock = stockHolding.stock.copy(currentPrice = currentPrice)

            // You can also update the values in the StockHolding as needed (e.g., total value, profit value, etc.)
        }

        // Save the updated portfolio with updated stock prices
        return portfolioRepository.save(portfolio)
    }
}
