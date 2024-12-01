package org.planx.common.models.transforming.converting.encoding.pddl

import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import org.planx.common.models.transforming.converting.ConvertingRequestBody
import org.planx.common.models.transforming.converting.encoding.EncodingRequest

/**
 * encoded PDDL4j problem wrapper
 */
data class PddlEncodingRequest(
    override var problem: PddlProblem? = null,
    override var domain: PddlDomain? = null
) : EncodingRequest<PddlProblem, PddlDomain>


data class PddlEncodingRequestBody(
    override var content: PddlEncodingRequest? = null,
    override var requestId: String = "",
    override var callStack: MutableStack<CallStack> = MutableStack()
) : ConvertingRequestBody<PddlEncodingRequest>
