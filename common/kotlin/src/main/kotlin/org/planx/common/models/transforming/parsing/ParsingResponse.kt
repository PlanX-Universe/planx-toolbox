package org.planx.common.models.transforming.parsing

import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.endpoint.solving.domain.Domain
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import org.planx.common.models.endpoint.solving.problem.Problem

interface ParsingResponse<P, D> where P : Problem, D : Domain {
    var domain: D?
    var problem: P?
}

interface ParsingResponseBody<P, D> : BaseMessageInterface
        where P : Problem, D : Domain {
    override val content: ParsingResponse<*, *>?
}
