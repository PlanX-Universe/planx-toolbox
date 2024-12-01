package org.planx.parsing.messaging.producer

import com.nhaarman.mockitokotlin2.*
import fr.uga.pddl4j.parser.Connective
import fr.uga.pddl4j.parser.Exp
import org.planx.common.models.transforming.parsing.ParsingResults
import org.planx.common.models.transforming.parsing.ParsingResultsBody
import org.planx.common.models.endpoint.solving.domain.PddlDomain
import org.planx.common.models.endpoint.solving.problem.PddlProblem
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.slf4j.Logger
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate

internal class SenderTest {

    private val log = mock<Logger>()
    private val rabbitTemplate = mock<RabbitTemplate>()

    private val sender = Sender(rabbitTemplate).apply {
        this.logger = log
    }

    private val validPDDLParsingResults: ParsingResults = ParsingResults(
            domain = PddlDomain(name = "ValidDomain", derivedPredicates = emptySet(), tasks = emptySet(),
                    predicates = emptySet(), types = emptySet(), methods = emptySet(), functions = emptySet(),
                    constants = emptySet(), requirements = emptySet(), constraints = null
            ),
            problem = PddlProblem(name = "ValidProblem", domain = "ValidDomain", requirements = emptySet(),
                    objects = emptySet(), initialState = emptyList(), goal = Exp(Connective.ATOM),
                    metric = null, constraints = null)
    )

    private val RESPONSE_TOPIC = "v1.router.managing-service"

    @Nested
    inner class PublishParsingResults {
        @Test
        fun `happy path - PDDL - valid results`() {
            sender.sendPddlParsingResults(validPDDLParsingResults, MessageProperties())

            val responseBody = ParsingResultsBody(
                    validPDDLParsingResults
            )
            verify(log).info("response send to $RESPONSE_TOPIC")
            verify(rabbitTemplate, times(1)).convertAndSend("v1.router.managing-service",
                    "toolbox.managing", responseBody)
        }

        @Test
        fun `unhappy path - ANY - RESPONSE_TOPIC doesn't exist`() {
            whenever(rabbitTemplate.convertAndSend(anyString(), anyString(), any<ParsingResultsBody>()))
                    .thenThrow(AmqpException::class.java)

            sender.sendPddlParsingResults(validPDDLParsingResults, MessageProperties())

            verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any<ParsingResultsBody>())
            verify(log).info("response send to $RESPONSE_TOPIC")
            verify(log, times(1)).error(any())

            verifyNoMoreInteractions(rabbitTemplate, log)
        }
    }
}