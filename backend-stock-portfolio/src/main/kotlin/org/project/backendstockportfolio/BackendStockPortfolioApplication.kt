package org.project.backendstockportfolio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BackendStockPortfolioApplication

fun main(args: Array<String>) {
    runApplication<BackendStockPortfolioApplication>(*args)
}
