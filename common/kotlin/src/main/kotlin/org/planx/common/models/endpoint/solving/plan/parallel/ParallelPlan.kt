package org.planx.common.models.endpoint.solving.plan.parallel

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.planx.common.models.endpoint.solving.plan.Action
import org.planx.common.models.endpoint.solving.plan.Plan

/**
 * This class implements a parallel plan based on the Graphplan planner semantic.
 * A parallel plan in the Graphplan planner semantic is a sequence of sets of actionSets.
 * Each action contained in a set are considered as unordered.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__TypeID__")
data class ParallelPlan<A>(
        override var cost: Double? = null,
        override var actions: List<Set<A>>? = emptyList()
) : Plan<List<Set<A>>> where A : Action
