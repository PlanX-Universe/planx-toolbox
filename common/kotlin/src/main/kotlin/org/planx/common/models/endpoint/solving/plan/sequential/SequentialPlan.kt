package org.planx.common.models.endpoint.solving.plan.sequential

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.SolvingResultsBody
import org.planx.common.models.endpoint.solving.plan.Action
import org.planx.common.models.endpoint.solving.plan.Plan

/**
 * This class implements a sequential plan.
 * Actions in a sequential plan must be consecutive.
 * (Order of actions matters!)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class SequentialPlan<A>(
    override var cost: Double? = null,
    override var actions: List<A>? = emptyList()
) : Plan<List<A>> where A : Action

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class SequentialPlanBody(
    override var content: SequentialPlan<*>?,
    override var requestId: String,
    override var callStack: MutableStack<CallStack>
) : SolvingResultsBody<SequentialPlan<*>>
