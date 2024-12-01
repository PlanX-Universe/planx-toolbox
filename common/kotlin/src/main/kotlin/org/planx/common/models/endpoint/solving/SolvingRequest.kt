package org.planx.common.models.endpoint.solving

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack

interface SolvingRequest {
    var planner: Planner?
    var language: Language?
    var domain: String?
    var problem: String?
}

interface SolvingRequestBody<R> : BaseMessageInterface where R : SolvingRequest {
    override val content: R?
}

data class UniversalSolvingRequest(
    override var planner: Planner? = null,
    override var language: Language? = null,
    override var domain: String? = "",
    override var problem: String? = ""
) : SolvingRequest

data class UniversalSolvingBody(
    override val content: UniversalSolvingRequest? = null,
    override var requestId: String = "",
    override var callStack: MutableStack<CallStack> = MutableStack()
) : SolvingRequestBody<UniversalSolvingRequest>
