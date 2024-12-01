package org.planx.common.models.endpoint.solving.plan

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
interface Plan<T> {
    var cost: Double?
    var actions: T?
}
