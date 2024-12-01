package org.planx.common.models.endpoint.solving.plan

import com.fasterxml.jackson.annotation.JacksonAnnotation
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
/**
 * only useful in combination with the domain!
 */
interface Action {
    /**
     * The name of the action.
     */
    var name: String

    /**
     * The moment it has to be executed
     */
    val momentInTime: Int?

    /**
     * The list of parameters of the operator.
     * The integer value correspond to the type of the parameters.
     */
    var parameters: List<String>

    /**
     * The values that represents the instantiated parameters of the operator.
     */
    var instantiations: List<String>?

    /**
     * The cost of the action.
     */
    var cost: Double

    /**
     * The duration of the action.
     */
    val duration: Double?

    /**
     * The preconditions of the action.
     */
    var preconditions: Any?

    /**
     * The list of effects of the action.
     */
    var effects: List<Any>?
}
