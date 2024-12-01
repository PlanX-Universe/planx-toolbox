package org.planx.parsing.models

import org.planx.common.models.endpoint.solving.domain.Domain
import org.planx.common.models.endpoint.solving.problem.Problem

interface ProblemAndDomainInterface<P, D> where P : Problem, D : Domain {
    var problem: P
    var domain: D
}

data class ProblemAndDomain<P, D>(
    override var problem: P,
    override var domain: D
) : ProblemAndDomainInterface<P, D> where P : Problem, D : Domain
