package org.planx.common.models.transforming.parsing

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.transforming.parsing.pddl.PddlParsingRequest

// TODO: find a solution to avoid JsonDeserialize strategy in hardcoded way
@JsonDeserialize(`as` = PddlParsingRequest::class)
interface ParsingRequest {
    // base64 encoded string
    var domain: String?

    // base64 encoded string
    var problem: String?
}

interface ParsingRequestBody : BaseMessageInterface {
    override val content: ParsingRequest?
}