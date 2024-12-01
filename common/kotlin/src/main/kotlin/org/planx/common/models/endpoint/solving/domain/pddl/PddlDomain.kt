package org.planx.common.models.endpoint.solving.domain.pddl

import org.planx.common.models.endpoint.solving.domain.Domain
import org.planx.common.models.pddl4j.*

data class PddlDomain(
    override val name: String = "",
    override val requirements: Set<RequireKeyData>? = null,
    override val types: Set<TypedSymbolData>? = null,
    override val predicates: Set<NamedTypedListData>? = null,
    override val functions: Set<NamedTypedListData>? = null,
    val constants: Set<TypedSymbolData>? = null,
    val constraints: ExpData? = null,
    val methods: Set<MethodData>? = null,
    val tasks: Set<OpData>? = null,
    val derivedPredicates: Set<DerivedPredicateData>? = null
) : Domain
