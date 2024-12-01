package org.planx.common.models.transforming.parsing.pddl

import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import org.planx.common.models.transforming.parsing.ParsingResponse
import org.planx.common.models.transforming.parsing.ParsingResponseBody
import java.util.*

data class PddlParsingResponseBody(
    override var requestId: String = UUID.randomUUID().toString(),
    override var callStack: MutableStack<CallStack> = MutableStack(),
    override val content: ParsingResponse<PddlProblem, PddlDomain>? = null
) : ParsingResponseBody<PddlProblem, PddlDomain>
