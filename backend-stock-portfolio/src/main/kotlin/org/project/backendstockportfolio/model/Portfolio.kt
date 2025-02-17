package org.project.backendstockportfolio.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "portfolios")
data class Portfolio(
    @Id val id: String? = null,
    val owner: String,
    val holdings: List<StockHolding> = emptyList()
)
