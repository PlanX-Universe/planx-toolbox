package org.planx.common.models.systemmanager.validating.pddl

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.plan.Action
import org.planx.common.models.endpoint.solving.plan.Plan
import org.planx.common.models.endpoint.solving.plan.PlanXAction
import org.planx.common.models.endpoint.solving.plan.sequential.SequentialPlan
import org.planx.common.models.systemmanager.validating.PlanValidationRequest
import org.planx.common.models.systemmanager.validating.PlanValidationRequestBody
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class PddlPlanValidationRequest(
    override var domain: String? = "",
    override var problem: String? = "",
    override var plan: SequentialPlan<PlanXAction>? = SequentialPlan()
) : PlanValidationRequest<SequentialPlan<PlanXAction>>

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class PddlPlanValidationRequestBody(
    override var requestId: String = UUID.randomUUID().toString(),
    override var callStack: MutableStack<CallStack> = MutableStack(),
    override val content: PddlPlanValidationRequest? = null
) : PlanValidationRequestBody<PddlPlanValidationRequest>
