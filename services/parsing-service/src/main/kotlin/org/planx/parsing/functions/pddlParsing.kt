package org.planx.parsing.functions

import fr.uga.pddl4j.parser.Message
import fr.uga.pddl4j.parser.Parser
import org.planx.parsing.exceptions.ParsingError
import org.planx.parsing.models.ProblemAndDomain
import org.slf4j.Logger
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import org.planx.common.models.pddl4j.PDDL4jConverter


@Throws(ParsingError::class)
fun pddl4jParsing(
    problem: String, domain: String,
    requestId: String, logger: Logger
): ProblemAndDomain<PddlProblem, PddlDomain> {
    val domainObj: PddlDomain
    val problemObj: PddlProblem
    val pddlParser = Parser()
    // domain, problem
    pddlParser.parseFromString(domain, problem)
    if (!pddlParser.errorManager.isEmpty) {
        // ERRORS!
        val sbErrors = StringBuilder()
        pddlParser.errorManager.getMessages(Message.Type.PARSER_ERROR).forEach {
            sbErrors.appendln(it.content)
        }
        if (sbErrors.isNotEmpty()) logger.error(sbErrors.toString())

        // DEBUG stuff!
        val sbDebugErrors = StringBuilder()
        pddlParser.errorManager.getMessages(Message.Type.LEXICAL_ERROR).forEach {
            sbDebugErrors.appendln(it.content)
        }
        if (sbDebugErrors.isNotEmpty()) logger.debug(sbDebugErrors.toString())

        // WARNINGS!
        val sbWarnings = StringBuilder()
        pddlParser.errorManager.getMessages(Message.Type.PARSER_WARNING).forEach {
            sbWarnings.appendln(it.content)
        }
        if (sbWarnings.isNotEmpty()) logger.warn(sbWarnings.toString())

        val sb = StringBuilder()
        sb.appendln("PARSER_ERROR:")
            .appendln(sbErrors.toString())
            .appendln("")
            .appendln("LEXICAL_ERROR:")
            .appendln(sbDebugErrors.toString())
            .appendln("")
            .appendln("PARSER_WARNING:")
            .appendln(sbWarnings.toString())
        logger.warn("Handle error: \n {}", sb.toString())
        throw ParsingError(requestId = requestId, internalErrors = sb.toString())
    }
    domainObj = PDDL4jConverter.convertPddl4j2Domain(pddlParser.domain)
    problemObj = PDDL4jConverter.convertPddl4j2Problem(pddlParser.problem)
    return ProblemAndDomain(problemObj, domainObj)
}
