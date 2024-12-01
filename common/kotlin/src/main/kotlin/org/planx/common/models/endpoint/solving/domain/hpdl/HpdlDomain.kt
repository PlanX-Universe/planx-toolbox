package org.planx.common.models.endpoint.solving.domain.hpdl

import org.planx.common.models.endpoint.solving.domain.Domain

data class HpdlDomain(
    val operators: Set<Any>? = null,
    val tasks: Set<Any>? = null,
    val methods: Set<Any>? = null,
    val axioms: Set<Any>? = null,
    override val name: String = "",
    override val requirements: Set<Any>? = null,
    override val types: Set<Any>? = null,
    override val predicates: Set<Any>? = null,
    override val functions: Set<Any>? = null
) : Domain