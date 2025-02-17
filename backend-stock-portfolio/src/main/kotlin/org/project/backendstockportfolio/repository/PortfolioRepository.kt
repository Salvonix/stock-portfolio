package org.project.backendstockportfolio.repository

import org.project.backendstockportfolio.model.Portfolio
import org.springframework.data.mongodb.repository.MongoRepository

interface PortfolioRepository : MongoRepository<Portfolio, String> {
    fun findByOwner(owner: String): Portfolio?
}
