package org.planx.common.models.systemmanager.validating.pddl

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.plan.PlanXAction
import org.planx.common.models.endpoint.solving.plan.sequential.SequentialPlan
import org.planx.common.models.systemmanager.validating.PlanValidationResponse
import org.planx.common.models.systemmanager.validating.PlanValidationResponseBody
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class PddlPlanValidationResponse(
    override val isValid: Boolean = true,
    override val plan: SequentialPlan<PlanXAction>? = null
) : PlanValidationResponse<SequentialPlan<PlanXAction>>

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class PddlPlanValidationResponseBody(
    override var requestId: String = UUID.randomUUID().toString(),
    override var callStack: MutableStack<CallStack> = MutableStack(),
    override val content: PlanValidationResponse<SequentialPlan<PlanXAction>>? = null
) : PlanValidationResponseBody
