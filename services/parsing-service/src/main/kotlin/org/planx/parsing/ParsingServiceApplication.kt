package org.planx.parsing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParsingServiceApplication

fun main(args: Array<String>) {
    runApplication<ParsingServiceApplication>(*args)
}
