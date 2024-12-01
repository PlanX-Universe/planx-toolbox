package org.planx.parsing.functions

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.planx.parsing.exceptions.ParsingError
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.slf4j.Logger

internal class PddlParsingKtTest {

    private val log = mock<Logger>()
    private val validDomain: String = """
            ;; logistics domain Typed version.
            ;;

            (define (domain logistics)
              (:requirements :strips :typing) 
              (:types truck
                      airplane - vehicle
                      package
                      vehicle - physobj
                      airport
                      location - place
                      city
                      place 
                      physobj - object)
              
              (:predicates 	(in-city ?loc - place ?city - city)
            		(at ?obj - physobj ?loc - place)
            		(in ?pkg - package ?veh - vehicle))
              
            (:action LOAD-TRUCK
               :parameters    (?pkg - package ?truck - truck ?loc - place)
               :precondition  (and (at ?truck ?loc) (at ?pkg ?loc))
               :effect        (and (not (at ?pkg ?loc)) (in ?pkg ?truck)))

            (:action LOAD-AIRPLANE
              :parameters   (?pkg - package ?airplane - airplane ?loc - place)
              :precondition (and (at ?pkg ?loc) (at ?airplane ?loc))
              :effect       (and (not (at ?pkg ?loc)) (in ?pkg ?airplane)))

            (:action UNLOAD-TRUCK
              :parameters   (?pkg - package ?truck - truck ?loc - place)
              :precondition (and (at ?truck ?loc) (in ?pkg ?truck))
              :effect       (and (not (in ?pkg ?truck)) (at ?pkg ?loc)))

            (:action UNLOAD-AIRPLANE
              :parameters    (?pkg - package ?airplane - airplane ?loc - place)
              :precondition  (and (in ?pkg ?airplane) (at ?airplane ?loc))
              :effect        (and (not (in ?pkg ?airplane)) (at ?pkg ?loc)))

            (:action DRIVE-TRUCK
              :parameters (?truck - truck ?loc-from - place ?loc-to - place ?city - city)
              :precondition
               (and (at ?truck ?loc-from) (in-city ?loc-from ?city) (in-city ?loc-to ?city))
              :effect
               (and (not (at ?truck ?loc-from)) (at ?truck ?loc-to)))

            (:action FLY-AIRPLANE
              :parameters (?airplane - airplane ?loc-from - airport ?loc-to - airport)
              :precondition
               (at ?airplane ?loc-from)
              :effect
               (and (not (at ?airplane ?loc-from)) (at ?airplane ?loc-to)))
            )
        """.trimIndent()
    private val validProblem: String = """
            (define (problem logistics-4-0)
            (:domain logistics)
            (:objects
             apn1 - airplane
             apt1 apt2 - airport
             pos2 pos1 - location
             cit2 cit1 - city
             tru2 tru1 - truck
             obj23 obj22 obj21 obj13 obj12 obj11 - package)

            (:init (at apn1 apt2) (at tru1 pos1) (at obj11 pos1)
             (at obj12 pos1) (at obj13 pos1) (at tru2 pos2) (at obj21 pos2) (at obj22 pos2)
             (at obj23 pos2) (in-city pos1 cit1) (in-city apt1 cit1) (in-city pos2 cit2)
             (in-city apt2 cit2))

            (:goal (and (at obj11 apt1) (at obj23 pos1) (at obj13 apt1) (at obj21 pos1)))
            )
        """.trimIndent()
    private val invalidProblem: String = """
            (define (problem logistics-4-0)
            (:domain logistics)
            (:objects
             apn1 - airplane
             apt1 apt2 - airport
             pos2 pos1 - location
             cit2 cit1 - city
             tru2 tru1 - truck
             obj23 obj22 obj21 obj13 obj12 obj11 - package)

            (:init (at apn1 apt2))

            (:goal (and (at obj11 apt1) (at obj23 pos1) (at obj13 apt1) (at obj21 pos1)))
            )
        """.trimIndent()

    @Test
    fun `happy path - valid strings`() {
        val result = pddl4jParsing(validProblem, validDomain, log)
        assertThat(result).isNotNull

        assertThat(result.domain).isNotNull
        assertThat(result.domain.name).isEqualTo("logistics")

        assertThat(result.problem).isNotNull
        assertThat(result.problem.name).isEqualTo("logistics-4-0")
        assertThat(result.problem.domain).isEqualTo("logistics")
    }

    @Test
    fun `happy path - empty strings`() {
        assertThatExceptionOfType(ParsingError::class.java).isThrownBy {
            pddl4jParsing("", "", log)
        }
    }

    @Test
    fun `unhappy path - invalid problem string`() {
        assertThatExceptionOfType(ParsingError::class.java).isThrownBy {
            pddl4jParsing(invalidProblem, "", log)
        }
        verify(log).error(any())
    }
}