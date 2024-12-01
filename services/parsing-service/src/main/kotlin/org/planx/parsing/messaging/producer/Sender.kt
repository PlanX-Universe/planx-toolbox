package org.planx.parsing.messaging.producer

import org.planx.parsing.functions.getLoggerFor
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.planx.common.models.BaseMessageInterface
import org.planx.common.models.CallStack
import org.planx.common.models.MutableStack

@Component
class Sender(var rabbitTemplate: RabbitTemplate) {
    var logger = getLoggerFor<Sender>()

    /**
     * generic send method
     */
    fun <T> sendNextRequest(
        requestBody: T,
        callStack: MutableStack<CallStack>,
        requestId: String
    ) where T : BaseMessageInterface {
        logger.info("Send request of type {}", requestBody::class.java.name)
        send(content = requestBody, addressElement = callStack.peek())
    }

    /**
     * generic send function
     */
    private fun <M> send(content: M, addressElement: CallStack) where M : BaseMessageInterface {
        try {
            logger.info("response send to ${addressElement.topic} (RequestId: ${content.requestId})")
            // is adding "__TypeId__" automatically
            rabbitTemplate.convertAndSend(addressElement.topic, addressElement.routingKey, content)
        } catch (e: AmqpException) {
            if (e.message != null) logger.error(e.message) else logger.error("Unknown AmqpException was thrown!")
        }
    }
}