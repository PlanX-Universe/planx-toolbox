package org.planx.common.models.endpoint.solving.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain

// FIXME: write own deserializer to enable generic behavior
@JsonDeserialize(`as` = PddlDomain::class)
interface Domain {
    /**
     * The name of the domain.
     */
    val name: String

    /**
     * The set of requirements.
     */
    val requirements: Set<Any>?

    /**
     * The set of types declared in the domain.
     */
    val types: Set<Any>?

    /**
     * The set of predicates used in the domain and the problem.
     */
    val predicates: Set<Any>?

    /**
     * The set of functions used in the domain and the problem.
     */
    val functions: Set<Any>?
}
