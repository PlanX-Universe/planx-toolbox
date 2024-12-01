package org.planx.common.models.endpoint.solving

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
interface SolvingResultsBody<T> : BaseMessageInterface {
    override var content: T?
    override var requestId: String
    override var callStack: MutableStack<CallStack>
}
