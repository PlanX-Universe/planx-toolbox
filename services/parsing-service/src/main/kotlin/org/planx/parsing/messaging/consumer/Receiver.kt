package org.planx.parsing.messaging.consumer

import org.planx.common.models.endpoint.solving.Language
import org.planx.common.models.transforming.parsing.ParsingRequestBody
import org.planx.common.models.transforming.parsing.pddl.PddlParsingRequestBody
import org.planx.parsing.functions.getLoggerFor
import org.planx.parsing.services.ParsingService
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.*

@Component
@RabbitListener(queues = ["\${planx.queues.request.name}"])
class Receiver(private var parsingService: ParsingService) {

    var logger = getLoggerFor<Receiver>()

    @RabbitHandler
    fun handleMessage(message: ParsingRequestBody, row: Message) {
        logger.info("Request received! (RequestID: ${message.requestId})")
        val msgProperties: MessageProperties = row.messageProperties

        // decode safe string
        val decoder = Base64.getDecoder()
        val decodedDomain = String(decoder.decode(message.content?.domain)).trimIndent()
        val decodedProblem = String(decoder.decode(message.content?.problem)).trimIndent()

        if (message is PddlParsingRequestBody) {
            parsingService.parseProblemAndDomain(
                problem = decodedProblem,
                domain = decodedDomain,
                language = Language.PDDL,
                callStack = message.callStack,
                requestId = message.requestId
            )
        } else {
            TODO("not implemented yet")
        }
    }
}
