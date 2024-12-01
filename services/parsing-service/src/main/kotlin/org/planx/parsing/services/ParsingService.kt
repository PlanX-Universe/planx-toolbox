package org.planx.parsing.services

import org.planx.parsing.exceptions.ParsingError
import org.planx.parsing.functions.getLoggerFor
import org.planx.parsing.messaging.producer.Sender
import org.planx.parsing.models.HpdlParser
import org.planx.parsing.models.PddlParser
import org.planx.parsing.models.ProblemAndDomain
import org.planx.parsing.models.Shop2Parser
import org.springframework.stereotype.Service
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.CallStack
import org.planx.common.models.FunctionalityType
import org.planx.common.models.MutableStack
import org.planx.common.models.endpoint.solving.Language
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import org.planx.common.models.transforming.converting.encoding.pddl.PddlEncodingRequest
import org.planx.common.models.transforming.converting.encoding.pddl.PddlEncodingRequestBody

@Service
class ParsingService(private var sender: Sender) {

    var logger = getLoggerFor<ParsingService>()

    /**
     * parses strings to objects
     *
     * @param problem [String]
     * @param domain [String]
     * @param language [Language] with default PDDL
     */
    fun parseProblemAndDomain(
        problem: String, domain: String,
        language: Language = Language.PDDL,
        callStack: MutableStack<CallStack>,
        requestId: String
    ) {
        val planXParser = when (language) {
            Language.PDDL -> PddlParser()
            Language.SHOP2 -> Shop2Parser()
            Language.HPDL -> HpdlParser()
            else -> throw ParsingError(requestId, "unsupported input language")
        }
        logger.info("Start parsing problem and domain (lang: {})", language.name)
        val problemAndDomain: ProblemAndDomain<*, *> = planXParser.parse(problem, domain, requestId)

        // remove parsing step
        callStack.pop()

        // get next process step
        val nextStep: CallStack = checkForCapableReturnType(callStack.peek(), problemAndDomain.javaClass)

        // add encoding step
        callStack.push(nextStep)

        // construct correct request body
        val requestBody = getNextRequestBody(callStack.peek(), problemAndDomain).apply {
            this.requestId = requestId
            this.callStack = callStack
        }

        sender.sendNextRequest(
            requestId = requestId,
            callStack = callStack,
            requestBody = requestBody
        )
    }

    private fun getNextRequestBody(
        stackElement: CallStack,
        problemAndDomain: ProblemAndDomain<*, *>
    ): BaseMessageInterface {
        // TODO: load correct request BodyClass for next call
        // use stackElement.replyClass
        if (problemAndDomain.domain is PddlDomain && problemAndDomain.problem is PddlProblem) {
            val pddlEncoding = PddlEncodingRequest(
                domain = problemAndDomain.domain as PddlDomain,
                problem = problemAndDomain.problem as PddlProblem
            )

            return PddlEncodingRequestBody(
                content = pddlEncoding
            )
        } else {
            TODO("Not implemented on the current version")
        }
    }

    private fun checkForCapableReturnType(
        stackElement: CallStack,
        parsingResults: Class<ProblemAndDomain<*, *>>
    ): CallStack {
        val replyClass: Class<*> = Class.forName(stackElement.replyClass)
        return if (replyClass.isAssignableFrom(parsingResults)) {
            stackElement
        } else {
            // TODO: check if its possible to provide the reply interface
            // The service should call the managing service to request all available functionalities, which fit the current in- and output format.
            CallStack(
                topic = FunctionalityType.Converting.topic,
                routingKey = FunctionalityType.Converting.routingKey,
                // use original reply type
                replyClass = stackElement.replyClass
            )
        }
    }
}