package org.planx.common.models.transforming.converting.encoding.pddl

import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.encoded.pddl4j.PddlEncodedProblem
import org.planx.common.models.transforming.converting.encoding.EncodingResponse
import org.planx.common.models.transforming.converting.encoding.EncodingResponseBody

data class PddlEncodingResponse(
    override var result: PddlEncodedProblem? = null
) : EncodingResponse<PddlEncodedProblem>

data class PddlEncodingResponseBody(
    override var content: EncodingResponse<PddlEncodedProblem>? = null,
    override var requestId: String = "",
    override var callStack: MutableStack<CallStack> = MutableStack()
) : EncodingResponseBody<PddlEncodingResponse, PddlEncodedProblem>
