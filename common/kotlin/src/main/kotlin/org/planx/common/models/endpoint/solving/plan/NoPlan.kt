package org.planx.common.models.endpoint.solving.plan

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.SolvingResultsBody

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class NoPlan(val message: String = "No plan found!")

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class NoPlanBody(
    override var callStack: MutableStack<CallStack>,
    override var requestId: String,
    override var content: NoPlan? = NoPlan()
) : SolvingResultsBody<NoPlan>
