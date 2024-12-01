package org.planx.common.models.endpoint.solving.encoded.pddl4j

import org.planx.common.models.endpoint.solving.encoded.EncodedProblem
import org.planx.common.models.pddl4j.BitExpData
import org.planx.common.models.pddl4j.BitOpData
import org.planx.common.models.pddl4j.InertiaData
import org.planx.common.models.pddl4j.IntExpData

/**
 * wrapper for [fr.uga.pddl4j.encoding.CodedProblem]
 */
data class PddlEncodedProblem(
    var types: List<String>? = null,
    var inferredDomains: List<Set<Int>>? = null,
    var domains: List<Set<Int>>? = null,
    var constants: List<String>? = null,
    var predicates: List<String>? = null,
    var predicatesSignatures: List<List<Int>>? = null,
    var functions: List<String>? = null,
    var functionsSignatures: List<List<Int>>? = null,
    var inertia: List<InertiaData>? = null,
    var relevantFacts: List<IntExpData>? = null,
    var operators: List<BitOpData>? = null,
    var goal: BitExpData? = null,
    var init: BitExpData? = null
) : EncodedProblem
