package org.planx.common.models.endpoint.solving.problem.pddl

import org.planx.common.models.endpoint.solving.problem.Problem
import org.planx.common.models.pddl4j.ExpData
import org.planx.common.models.pddl4j.RequireKeyData
import org.planx.common.models.pddl4j.TypedSymbolData

data class PddlProblem(
    override val name: String = "",
    override val domain: String = "",
    override val initialState: List<ExpData>? = null,
    override val objects: Set<TypedSymbolData>? = null,
    override val requirements: Set<RequireKeyData>? = null,
    override val goal: ExpData? = null,
    val constraints: ExpData? = null,
    val metric: ExpData? = null
) : Problem
