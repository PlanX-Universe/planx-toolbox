package org.planx.parsing.messaging.consumer

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.planx.common.models.endpoint.solving.Language
import org.planx.common.models.transforming.parsing.ParsingRequest
import org.planx.common.models.transforming.parsing.ParsingRequestBody
import org.planx.parsing.services.ParsingService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import org.slf4j.Logger
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties

internal class ReceiverTest {

    private val log = mock<Logger>()
    private val parsingService = mock<ParsingService>()
    private val receiver = Receiver(parsingService).apply {
        this.logger = log
    }

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun init() {
        Assertions.assertThat(receiver).isNotNull
    }

    @Nested
    inner class HandleMessage {
        private val validEncodedDomain: String = "OzsgbG9naXN0aWNzIGRvbWFpbiBUeXBlZCB2ZXJzaW9uLgogICAgICAgICAgICA7OwoKICAgICAgICAgICAgKGRlZmluZSAoZG9tYWluIGxvZ2lzdGljcykKICAgICAgICAgICAgICAoOnJlcXVpcmVtZW50cyA6c3RyaXBzIDp0eXBpbmcpIAogICAgICAgICAgICAgICg6dHlwZXMgdHJ1Y2sKICAgICAgICAgICAgICAgICAgICAgIGFpcnBsYW5lIC0gdmVoaWNsZQogICAgICAgICAgICAgICAgICAgICAgcGFja2FnZQogICAgICAgICAgICAgICAgICAgICAgdmVoaWNsZSAtIHBoeXNvYmoKICAgICAgICAgICAgICAgICAgICAgIGFpcnBvcnQKICAgICAgICAgICAgICAgICAgICAgIGxvY2F0aW9uIC0gcGxhY2UKICAgICAgICAgICAgICAgICAgICAgIGNpdHkKICAgICAgICAgICAgICAgICAgICAgIHBsYWNlIAogICAgICAgICAgICAgICAgICAgICAgcGh5c29iaiAtIG9iamVjdCkKICAgICAgICAgICAgICAKICAgICAgICAgICAgICAoOnByZWRpY2F0ZXMgCShpbi1jaXR5ID9sb2MgLSBwbGFjZSA/Y2l0eSAtIGNpdHkpCiAgICAgICAgICAgIAkJKGF0ID9vYmogLSBwaHlzb2JqID9sb2MgLSBwbGFjZSkKICAgICAgICAgICAgCQkoaW4gP3BrZyAtIHBhY2thZ2UgP3ZlaCAtIHZlaGljbGUpKQogICAgICAgICAgICAgIAogICAgICAgICAgICAoOmFjdGlvbiBMT0FELVRSVUNLCiAgICAgICAgICAgICAgIDpwYXJhbWV0ZXJzICAgICg/cGtnIC0gcGFja2FnZSA/dHJ1Y2sgLSB0cnVjayA/bG9jIC0gcGxhY2UpCiAgICAgICAgICAgICAgIDpwcmVjb25kaXRpb24gIChhbmQgKGF0ID90cnVjayA/bG9jKSAoYXQgP3BrZyA/bG9jKSkKICAgICAgICAgICAgICAgOmVmZmVjdCAgICAgICAgKGFuZCAobm90IChhdCA/cGtnID9sb2MpKSAoaW4gP3BrZyA/dHJ1Y2spKSkKCiAgICAgICAgICAgICg6YWN0aW9uIExPQUQtQUlSUExBTkUKICAgICAgICAgICAgICA6cGFyYW1ldGVycyAgICg/cGtnIC0gcGFja2FnZSA/YWlycGxhbmUgLSBhaXJwbGFuZSA/bG9jIC0gcGxhY2UpCiAgICAgICAgICAgICAgOnByZWNvbmRpdGlvbiAoYW5kIChhdCA/cGtnID9sb2MpIChhdCA/YWlycGxhbmUgP2xvYykpCiAgICAgICAgICAgICAgOmVmZmVjdCAgICAgICAoYW5kIChub3QgKGF0ID9wa2cgP2xvYykpIChpbiA/cGtnID9haXJwbGFuZSkpKQoKICAgICAgICAgICAgKDphY3Rpb24gVU5MT0FELVRSVUNLCiAgICAgICAgICAgICAgOnBhcmFtZXRlcnMgICAoP3BrZyAtIHBhY2thZ2UgP3RydWNrIC0gdHJ1Y2sgP2xvYyAtIHBsYWNlKQogICAgICAgICAgICAgIDpwcmVjb25kaXRpb24gKGFuZCAoYXQgP3RydWNrID9sb2MpIChpbiA/cGtnID90cnVjaykpCiAgICAgICAgICAgICAgOmVmZmVjdCAgICAgICAoYW5kIChub3QgKGluID9wa2cgP3RydWNrKSkgKGF0ID9wa2cgP2xvYykpKQoKICAgICAgICAgICAgKDphY3Rpb24gVU5MT0FELUFJUlBMQU5FCiAgICAgICAgICAgICAgOnBhcmFtZXRlcnMgICAgKD9wa2cgLSBwYWNrYWdlID9haXJwbGFuZSAtIGFpcnBsYW5lID9sb2MgLSBwbGFjZSkKICAgICAgICAgICAgICA6cHJlY29uZGl0aW9uICAoYW5kIChpbiA/cGtnID9haXJwbGFuZSkgKGF0ID9haXJwbGFuZSA/bG9jKSkKICAgICAgICAgICAgICA6ZWZmZWN0ICAgICAgICAoYW5kIChub3QgKGluID9wa2cgP2FpcnBsYW5lKSkgKGF0ID9wa2cgP2xvYykpKQoKICAgICAgICAgICAgKDphY3Rpb24gRFJJVkUtVFJVQ0sKICAgICAgICAgICAgICA6cGFyYW1ldGVycyAoP3RydWNrIC0gdHJ1Y2sgP2xvYy1mcm9tIC0gcGxhY2UgP2xvYy10byAtIHBsYWNlID9jaXR5IC0gY2l0eSkKICAgICAgICAgICAgICA6cHJlY29uZGl0aW9uCiAgICAgICAgICAgICAgIChhbmQgKGF0ID90cnVjayA/bG9jLWZyb20pIChpbi1jaXR5ID9sb2MtZnJvbSA/Y2l0eSkgKGluLWNpdHkgP2xvYy10byA/Y2l0eSkpCiAgICAgICAgICAgICAgOmVmZmVjdAogICAgICAgICAgICAgICAoYW5kIChub3QgKGF0ID90cnVjayA/bG9jLWZyb20pKSAoYXQgP3RydWNrID9sb2MtdG8pKSkKCiAgICAgICAgICAgICg6YWN0aW9uIEZMWS1BSVJQTEFORQogICAgICAgICAgICAgIDpwYXJhbWV0ZXJzICg/YWlycGxhbmUgLSBhaXJwbGFuZSA/bG9jLWZyb20gLSBhaXJwb3J0ID9sb2MtdG8gLSBhaXJwb3J0KQogICAgICAgICAgICAgIDpwcmVjb25kaXRpb24KICAgICAgICAgICAgICAgKGF0ID9haXJwbGFuZSA/bG9jLWZyb20pCiAgICAgICAgICAgICAgOmVmZmVjdAogICAgICAgICAgICAgICAoYW5kIChub3QgKGF0ID9haXJwbGFuZSA/bG9jLWZyb20pKSAoYXQgP2FpcnBsYW5lID9sb2MtdG8pKSkKICAgICAgICAgICAgKQ=="
        private val validEncodedProblem: String = "KGRlZmluZSAocHJvYmxlbSBsb2dpc3RpY3MtNC0wKQogICAgICAgICAgICAoOmRvbWFpbiBsb2dpc3RpY3MpCiAgICAgICAgICAgICg6b2JqZWN0cwogICAgICAgICAgICAgYXBuMSAtIGFpcnBsYW5lCiAgICAgICAgICAgICBhcHQxIGFwdDIgLSBhaXJwb3J0CiAgICAgICAgICAgICBwb3MyIHBvczEgLSBsb2NhdGlvbgogICAgICAgICAgICAgY2l0MiBjaXQxIC0gY2l0eQogICAgICAgICAgICAgdHJ1MiB0cnUxIC0gdHJ1Y2sKICAgICAgICAgICAgIG9iajIzIG9iajIyIG9iajIxIG9iajEzIG9iajEyIG9iajExIC0gcGFja2FnZSkKCiAgICAgICAgICAgICg6aW5pdCAoYXQgYXBuMSBhcHQyKSAoYXQgdHJ1MSBwb3MxKSAoYXQgb2JqMTEgcG9zMSkKICAgICAgICAgICAgIChhdCBvYmoxMiBwb3MxKSAoYXQgb2JqMTMgcG9zMSkgKGF0IHRydTIgcG9zMikgKGF0IG9iajIxIHBvczIpIChhdCBvYmoyMiBwb3MyKQogICAgICAgICAgICAgKGF0IG9iajIzIHBvczIpIChpbi1jaXR5IHBvczEgY2l0MSkgKGluLWNpdHkgYXB0MSBjaXQxKSAoaW4tY2l0eSBwb3MyIGNpdDIpCiAgICAgICAgICAgICAoaW4tY2l0eSBhcHQyIGNpdDIpKQoKICAgICAgICAgICAgKDpnb2FsIChhbmQgKGF0IG9iajExIGFwdDEpIChhdCBvYmoyMyBwb3MxKSAoYXQgb2JqMTMgYXB0MSkgKGF0IG9iajIxIHBvczEpKSkKICAgICAgICAgICAgKQ=="
        private val validDecodedDomain: String = """
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
        """.trim()
        private val validDecodedProblem: String = """
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
        """.trim()

        private val validRequest = ParsingRequestBody(ParsingRequest()).apply {
            this.content.domain = validEncodedDomain
            this.content.problem = validEncodedProblem
        }

        @Test
        fun `happy path - valid request`() {
            receiver.handleMessage(validRequest, Message(null, MessageProperties()))
            verify(log).info(eq("Request received!"))
            verify(parsingService, Times(1))
                    .parseProblemAndDomain(validDecodedProblem, validDecodedDomain, Language.PDDL, any())
        }
    }
}