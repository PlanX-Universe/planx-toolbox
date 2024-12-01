package org.planx.parsing.models

import org.planx.parsing.exceptions.ParsingError
import org.planx.parsing.functions.getLoggerFor
import org.planx.parsing.functions.pddl4jParsing
import org.slf4j.Logger
import org.planx.common.models.endpoint.solving.domain.Domain
import org.planx.common.models.endpoint.solving.domain.hpdl.HpdlDomain
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.domain.shop.Shop2Domain
import org.planx.common.models.endpoint.solving.problem.Problem
import org.planx.common.models.endpoint.solving.problem.hpdl.HpdlProblem
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import org.planx.common.models.endpoint.solving.problem.shop.Shop2Problem

interface PlanXParser<P, D> where P : Problem, D : Domain {
    val logger: Logger

    @Throws(ParsingError::class)
    fun parse(problem: String, domain: String, requestId: String): ProblemAndDomain<P, D>
}

class PddlParser() : PlanXParser<PddlProblem, PddlDomain> {
    override val logger: Logger = getLoggerFor<PddlParser>()

    override fun parse(problem: String, domain: String, requestId: String): ProblemAndDomain<PddlProblem, PddlDomain> {
        return pddl4jParsing(
            problem = problem, domain = domain,
            requestId = requestId, logger = logger
        )
    }
}

/**
 * Not implemented yet!
 * TODO: implement or outsource to other functionality!
 */
class Shop2Parser() : PlanXParser<Shop2Problem, Shop2Domain> {
    override val logger: Logger = getLoggerFor<Shop2Parser>()

    override fun parse(
        problem: String,
        domain: String,
        requestId: String
    ): ProblemAndDomain<Shop2Problem, Shop2Domain> {
        TODO("Not implemented yet!")
        // return ProblemAndDomain(problem = JShop2Problem(), domain = JShop2Domain())
    }
}

/**
 * Not implemented yet!
 * TODO: implement or outsource to other functionality!
 */
class HpdlParser() : PlanXParser<HpdlProblem, HpdlDomain> {
    override val logger: Logger = getLoggerFor<PddlParser>()

    override fun parse(problem: String, domain: String, requestId: String): ProblemAndDomain<HpdlProblem, HpdlDomain> {
        TODO("Not implemented yet!")
        // return ProblemAndDomain(problem = HpdlProblem(), domain = HpdlDomain())
    }
}
