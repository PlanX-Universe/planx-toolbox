package org.planx.common.models.endpoint.solving.plan

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(value = JsonInclude.Include.NON_NULL)
data class PlanXAction(
    override var name: String = "",
    override var parameters: List<String> = emptyList(),
    override var instantiations: List<String>? = null,
    override var cost: Double = 1.0,
    override val duration: Double? = 1.0,
    override val momentInTime: Int? = 0,
    override var preconditions: Any? = null,
    override var effects: List<Any>? = null
) : Action
