package org.planx.common.models.systemmanager.validating

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.endpoint.solving.plan.Plan
import org.planx.common.models.endpoint.solving.plan.PlanXAction
import org.planx.common.models.endpoint.solving.plan.sequential.SequentialPlan

@JsonDeserialize(`as` = PlanValidationOut::class)
interface PlanValidationResponse<P> where P : Plan<*> {
    val isValid: Boolean
    val plan: P?
}

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
interface PlanValidationResponseBody : BaseMessageInterface {
    override val content: PlanValidationResponse<SequentialPlan<PlanXAction>>?
}