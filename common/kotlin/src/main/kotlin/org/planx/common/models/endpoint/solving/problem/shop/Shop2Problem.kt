package org.planx.common.models.endpoint.solving.problem.shop

import org.planx.common.models.endpoint.solving.problem.Problem

data class Shop2Problem(
    override val name: String = "",
    override val domain: String = "",
    override val requirements: Set<Any>? = null,
    override val objects: Set<Any>? = null,
    override val initialState: List<Any>? = null,
    override val goal: Any? = null
) : Problem