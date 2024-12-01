package org.planx.common.models.transforming.converting.encoding

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.endpoint.solving.encoded.EncodedProblem
import org.planx.common.models.transforming.converting.ConvertingResponse
import org.planx.common.models.transforming.converting.encoding.pddl.PddlEncodingResponse

@JsonDeserialize(`as` = PddlEncodingResponse::class)
interface EncodingResponse<T> : ConvertingResponse<T>
        where T : EncodedProblem {
    override var result: T?
}

interface EncodingResponseBody<C, R> :
    BaseMessageInterface where C : EncodingResponse<R>, R : EncodedProblem {
    override var content: EncodingResponse<R>?
}
