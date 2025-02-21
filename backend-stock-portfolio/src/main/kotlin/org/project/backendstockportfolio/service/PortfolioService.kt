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
    //Create new portfolio with an owner and no held stock
    fun createPortfolio(owner: String): Portfolio {
        val portfolio = Portfolio(owner = owner)
        return portfolioRepository.insert(portfolio)
    }

    //Fetch the portfolio, the stock with the current price and then add it to the list of held stocks of the portfolio
    fun addStockToPortfolio(portfolioId: String, stockSymbol: String, shares: Int): Portfolio {
        val portfolio = portfolioRepository.findById(portfolioId).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found") }
        val stock = stockRepository.findBySymbol(stockSymbol) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"Stock not found")

        val newHolding = StockHolding(stock, shares, stock.currentPrice)
        val updatedPortfolio = portfolio.copy(holdings = portfolio.holdings + newHolding)

        return portfolioRepository.save(updatedPortfolio)
    }

    //Find Owner of a portfolio simply by name
    fun findByOwner(owner: String): Portfolio {
        return portfolioRepository.findByOwner(owner) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found")
    }

    //Ensures that all operations inside the method execute within a single database transaction
    @Transactional
    fun updateAllStockPricesInPortfolio(portfolioId: String): Portfolio {
        // Fetch the portfolio in question
        val portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow { Exception("Portfolio not found") }

        portfolio.holdings.forEach { stockHolding ->
            // Update the stock price in the StockHolding object depending on the heritage class
            stockHolding.stock = stockHolding.stock.interestOnPrice()
        }

        // Save the updated portfolio with updated stock prices
        return portfolioRepository.save(portfolio)
    }
}
