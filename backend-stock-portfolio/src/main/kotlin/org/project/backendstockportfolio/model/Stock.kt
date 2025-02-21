package org.project.backendstockportfolio.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

//Trying to use JsonType and JsonSub to do some good polymorphism
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = CommonStock::class, name = "common"),
    JsonSubTypes.Type(value = PreferredStock::class, name = "preferred")
)

//Base Stock class
@Document(collection = "stocks")
abstract class Stock(
    @Id val id: String? = null,
    open val name: String,
    open val symbol: String,
    open val currentPrice: Double
) {
    abstract fun interestOnPrice(): Stock
}
