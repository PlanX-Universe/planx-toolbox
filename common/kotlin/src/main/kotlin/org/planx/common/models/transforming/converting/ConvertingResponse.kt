package org.planx.common.models.transforming.converting

import org.planx.common.models.BaseMessageInterface

interface ConvertingResponse<T> {
    var result: T?
}

interface ConvertingResponseBody<T> : BaseMessageInterface {
    override var content: ConvertingResponse<T>?
}
