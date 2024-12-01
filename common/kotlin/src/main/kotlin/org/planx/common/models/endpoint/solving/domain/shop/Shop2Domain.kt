package org.planx.common.models.endpoint.solving.domain.shop

import org.planx.common.models.endpoint.solving.domain.Domain


data class Shop2Domain(
    override val name: String = "",
    override val requirements: Set<Any>? = null,
    override val types: Set<Any>? = null,
    override val predicates: Set<Any>? = null,
    override val functions: Set<Any>? = null
) : Domain
