package org.planx.common.models.endpoint.solving.problem

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem

// FIXME: write own deserializer to enable generic behavior
@JsonDeserialize(`as` = PddlProblem::class)
interface Problem {
    /**
     * The name of the problem.
     */
    val name: String

    /**
     * The name of the domain.
     */
    val domain: String

    /**
     * The set of requirements.
     */
    val requirements: Set<Any>?

    /**
     * The set of objects declared in the problem.
     */
    val objects: Set<Any>?

    /**
     * The list of initial state declared in the problem.
     */
    val initialState: List<Any>?

    /**
     * The goal of the problem.
     */
    val goal: Any?
}
