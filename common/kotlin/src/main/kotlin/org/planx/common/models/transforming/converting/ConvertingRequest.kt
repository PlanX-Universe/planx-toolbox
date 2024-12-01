package org.planx.common.models.transforming.converting

import org.planx.common.models.BaseMessageInterface

interface ConvertingRequest

interface ConvertingRequestBody<T> : BaseMessageInterface where T : ConvertingRequest {
    override var content: T?
}

