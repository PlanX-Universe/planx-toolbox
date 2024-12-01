package org.planx.common.models

enum class FunctionalityType(val topic: String, val routingKey: String) {
    Managing("v1.router.managing-service", "toolbox"),
    Parsing("v1.transforming.parsing-service", "toolbox.pddl4j"),
    Solving("v1.endpoint.solving-service", "toolbox.pddl4j"),
    Converting("v1.transforming.converting-service", "toolbox.pddl4j-encoding")
}
