package org.planx.common.models.transforming.parsing.pddl

import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack
import org.planx.common.models.transforming.parsing.ParsingRequest
import org.planx.common.models.transforming.parsing.ParsingRequestBody
import java.util.*

data class PddlParsingRequest(
        override var domain: String? = "",
        override var problem: String? = ""
) : ParsingRequest

data class PddlParsingRequestBody(
    override var requestId: String = UUID.randomUUID().toString(),
    override var callStack: MutableStack<CallStack> = MutableStack(),
    override val content: ParsingRequest? = null
) : ParsingRequestBody
