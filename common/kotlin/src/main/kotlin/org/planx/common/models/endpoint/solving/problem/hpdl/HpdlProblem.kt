package org.planx.common.models.endpoint.solving.problem.hpdl

import org.planx.common.models.endpoint.solving.problem.Problem

class HpdlProblem(
    override val name: String = "",
    override val domain: String = "",
    override val requirements: Set<Any>? = null,
    override val objects: Set<Any>? = null,
    override val initialState: List<Any>? = null,
    override val goal: Any? = null
) : Problem