package org.planx.common.models

import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Base massage format
 *
 * @author Sebastian Graef
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
interface BaseMessageInterface {
    var requestId: String
    var callStack: org.planx.common.models.MutableStack<org.planx.common.models.CallStack>
    val content: Any?
}

/**
 * implementation of the "Routing Slip" Pattern
 * see https://www.enterpriseintegrationpatterns.com/patterns/messaging/RoutingTable.html
 */
data class CallStack(
    val topic: String = "",
    val routingKey: String = "",
    val state: Map<String, String>? = null,
    val replyClass: String = Map::class.java.name
)
