package org.planx.common.models.systemmanager.validating

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.endpoint.solving.plan.Plan
import org.planx.common.models.systemmanager.validating.pddl.PddlPlanValidationRequest

@JsonDeserialize(`as` = PddlPlanValidationRequest::class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
interface PlanValidationRequest<T> where T : Plan<*> {
    // base64 encoded string
    var domain: String?

    // base64 encoded string
    var problem: String?

    var plan: T?
}

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
interface PlanValidationRequestBody<T> : BaseMessageInterface {
    override val content: T?
}