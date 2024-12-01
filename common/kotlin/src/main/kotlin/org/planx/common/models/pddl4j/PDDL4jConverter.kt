package org.planx.common.models.pddl4j

import fr.uga.pddl4j.encoding.CodedProblem
import fr.uga.pddl4j.encoding.Inertia
import fr.uga.pddl4j.parser.*
import fr.uga.pddl4j.util.*
import org.planx.common.models.endpoint.solving.domain.pddl.PddlDomain
import org.planx.common.models.endpoint.solving.encoded.pddl4j.PddlEncodedProblem
import org.planx.common.models.endpoint.solving.problem.pddl.PddlProblem
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.util.*

/**
 * This class provides static functions for converting between PDDL4J objects and UIR-Data objects
 * The wrapper is written for a specific VERSION! Updates may change the datastructures!
 *
 * @version PDDL4J 3.8.3
 */
class PDDL4jConverter {
    companion object {
        /**
         * This method converts a Pddl Problem (UIR-Data object) to a PDDL4J object [Problem]
         * @param pddlProblem of type [PddlProblem]
         */
        fun convertProblem2Pddl4j(pddlProblem: PddlProblem): Problem {
            val result = Problem(Symbol(Symbol.Kind.PROBLEM, pddlProblem.name))
            result.apply {
                this.constraints = data2exp(pddlProblem.constraints)
                this.domain = Symbol(Symbol.Kind.DOMAIN, pddlProblem.domain)
                this.goal = data2exp(pddlProblem.goal)
                this.metric = data2exp(pddlProblem.metric)
            }
            pddlProblem.initialState
                ?.map {
                    data2exp(it)
                }
                ?.forEach {
                    result.addInitialFact(it)
                }
            pddlProblem.objects
                ?.map {
                    data2typedSymbol(it)
                }
                ?.forEach {
                    result.addObject(it)
                }
            pddlProblem.requirements
                ?.map {
                    data2RequireKey(it)
                }
                ?.forEach {
                    result.addRequirement(it)
                }

            return result
        }

        fun convertDomain2Pddl4j(pddlDomain: PddlDomain): Domain {
            val result = Domain(Symbol(Symbol.Kind.DOMAIN, pddlDomain.name))
            result.apply {
                this.constraints = data2exp(pddlDomain.constraints)
            }
            pddlDomain.requirements
                ?.map {
                    data2RequireKey(it)
                }
                ?.forEach {
                    result.addRequirement(it)
                }
            pddlDomain.types
                ?.map {
                    data2typedSymbol(it)
                }
                ?.forEach {
                    result.addType(it)
                }
            pddlDomain.predicates
                ?.map {
                    data2NamedTypedList(it)
                }
                ?.forEach {
                    result.addPredicate(it)
                }
            pddlDomain.functions
                ?.map {
                    data2NamedTypedList(it)
                }
                ?.forEach {
                    result.addFunction(it)
                }
            pddlDomain.constants
                ?.map {
                    data2typedSymbol(it)
                }
                ?.forEach {
                    result.addConstant(it)
                }
            pddlDomain.methods
                ?.map {
                    data2Method(it)
                }
                ?.forEach {
                    result.addMethod(it)
                }
            pddlDomain.tasks
                ?.map {
                    data2Op(it)
                }
                ?.forEach {
                    result.addOperator(it)
                }
            pddlDomain.derivedPredicates
                ?.map {
                    data2DerivedPredicate(it)
                }
                ?.forEach {
                    result.addDerivedPredicate(it)
                }
            return result
        }

        fun convertPddl4j2Domain(domain: Domain): PddlDomain {
            return PddlDomain(
                requirements = domain.requirements?.map {
                    requireKey2Data(it)
                }?.toSet(),
                types = domain.types?.map { it ->
                    typedSymbol2Data(it)
                }?.toSet(),
                predicates = domain.predicates?.map {
                    namedTypedList2Data(it)
                }?.toSet(),
                functions = domain.functions?.map {
                    namedTypedList2Data(it)
                }?.toSet(),
                constants = domain.constants?.map {
                    typedSymbol2Data(it)
                }?.toSet(),
                constraints = exp2Data(domain.constraints),
                methods = domain.methods?.map {
                    method2Data(it)
                }?.toSet(),
                tasks = domain.operators?.map {
                    op2Data(it)
                }?.toSet(),
                derivedPredicates = domain.derivesPredicates?.map {
                    derivedPredicate2Data(it)
                }?.toSet(),
                name = domain.name.image
            )
        }

        fun convertPddl4j2Problem(problem: Problem): PddlProblem {
            return PddlProblem(
                name = problem.name.image,
                goal = exp2Data(problem.goal),
                initialState = problem.init?.map {
                    exp2Data(it)!!
                },
                objects = problem.objects?.map {
                    typedSymbol2Data(it)
                }?.toSet(),
                requirements = problem.requirements?.map {
                    requireKey2Data(it)
                }?.toSet(),
                constraints = exp2Data(problem.constraints),
                metric = exp2Data(problem.metric),
                domain = problem.domain.image
            )
        }

        fun convertEncodedProblem2Pddl4j(encodedProblem: PddlEncodedProblem): CodedProblem {
            // hack to access private or default constructor by a reflection
            val javaClass = CodedProblem::class.java
            val constructor: Constructor<CodedProblem> = javaClass.getDeclaredConstructor()
            constructor.isAccessible = true
            // init
            val result = constructor.newInstance()

            val c = result.javaClass
            // set types
            overwritePrivateField(c, result, encodedProblem.types, "types")
            // set inferredDomains
            overwritePrivateField(c, result, encodedProblem.inferredDomains, "inferredDomains")
            // set domains
            overwritePrivateField(c, result, encodedProblem.domains, "domains")
            // set constants
            overwritePrivateField(c, result, encodedProblem.constants, "constants")
            // set predicates
            overwritePrivateField(c, result, encodedProblem.predicates, "predicates")
            // set predicatesSignatures
            overwritePrivateField(c, result, encodedProblem.predicatesSignatures, "predicatesSignatures")
            // set functions
            overwritePrivateField(c, result, encodedProblem.functions, "functions")
            // set functionsSignatures
            overwritePrivateField(c, result, encodedProblem.functionsSignatures, "functionsSignatures")
            // set inertia
            overwritePrivateField(
                c, result,
                encodedProblem.inertia?.map { data2Inertia(it) }, "inertia"
            )
            // set relevantFacts
            overwritePrivateField(
                c, result,
                encodedProblem.relevantFacts?.map { data2IntExp(it) }, "relevantFacts"
            )
            // set operators
            overwritePrivateField(c, result, encodedProblem.operators?.map { data2BitOp(it) }, "operators")
            // set goal
            overwritePrivateField(c, result, data2BitExp(encodedProblem.goal!!), "goal")
            // set init
            overwritePrivateField(c, result, data2BitExp(encodedProblem.init!!), "init")
            return result
        }

        fun convertPddl4j2EncodedProblem(codedProblem: CodedProblem): PddlEncodedProblem {
            val javaClass = CodedProblem::class.java



            return PddlEncodedProblem(
                types = codedProblem.types,
                inferredDomains = getPrivateField(javaClass, "inferredDomains", codedProblem),
                domains = codedProblem.domains,
                constants = codedProblem.constants,
                predicates = codedProblem.predicates,
                predicatesSignatures = codedProblem.predicatesSignatures,
                functions = getPrivateField(javaClass, "functions", codedProblem),
                functionsSignatures = codedProblem.functionsSignatures,
                inertia = codedProblem.inertia.map {
                    inertia2Data(it)
                },
                relevantFacts = codedProblem.relevantFacts.map {
                    intExp2Data(it)
                },
                operators = codedProblem.operators?.map {
                    bitOp2Data(it)
                },
                goal = bitExp2Data(codedProblem.goal),
                init = bitExp2Data(codedProblem.init)
            )
        }


        private fun data2Inertia(inertiaData: InertiaData): Inertia {
            return when (inertiaData) {
                InertiaData.POSITIVE -> Inertia.POSITIVE
                InertiaData.NEGATIVE -> Inertia.NEGATIVE
                InertiaData.FLUENT -> Inertia.FLUENT
                InertiaData.INERTIA -> Inertia.INERTIA
            }
        }

        private fun data2IntExp(intExpData: IntExpData): IntExp {
            return IntExp(data2Connective(intExpData.connective))
        }

        private fun data2Connective(connectiveData: ConnectiveData?): Connective? {
            return when (connectiveData) {
                ConnectiveData.ATOM -> Connective.ATOM
                ConnectiveData.EQUAL_ATOM -> Connective.EQUAL_ATOM
                ConnectiveData.NOT -> Connective.NOT
                ConnectiveData.AND -> Connective.AND
                ConnectiveData.OR -> Connective.OR
                ConnectiveData.FORALL -> Connective.FORALL
                ConnectiveData.EXISTS -> Connective.EXISTS
                ConnectiveData.F_EXP -> Connective.F_EXP
                ConnectiveData.F_EXP_T -> Connective.F_EXP_T
                ConnectiveData.TIME_VAR -> Connective.TIME_VAR
                ConnectiveData.FN_ATOM -> Connective.FN_ATOM
                ConnectiveData.FN_HEAD -> Connective.FN_HEAD
                ConnectiveData.DURATION_ATOM -> Connective.DURATION_ATOM
                ConnectiveData.LESS -> Connective.LESS
                ConnectiveData.LESS_OR_EQUAL -> Connective.LESS_OR_EQUAL
                ConnectiveData.EQUAL -> Connective.EQUAL
                ConnectiveData.GREATER -> Connective.GREATER
                ConnectiveData.GREATER_OR_EQUAL -> Connective.GREATER_OR_EQUAL
                ConnectiveData.MUL -> Connective.MUL
                ConnectiveData.DIV -> Connective.DIV
                ConnectiveData.MINUS -> Connective.MINUS
                ConnectiveData.UMINUS -> Connective.UMINUS
                ConnectiveData.PLUS -> Connective.PLUS
                ConnectiveData.NUMBER -> Connective.NUMBER
                ConnectiveData.ASSIGN -> Connective.ASSIGN
                ConnectiveData.INCREASE -> Connective.INCREASE
                ConnectiveData.DECREASE -> Connective.DECREASE
                ConnectiveData.SCALE_UP -> Connective.SCALE_UP
                ConnectiveData.SCALE_DOWN -> Connective.SCALE_DOWN
                ConnectiveData.AT_START -> Connective.AT_START
                ConnectiveData.AT_END -> Connective.AT_END
                ConnectiveData.OVER_ALL -> Connective.OVER_ALL
                ConnectiveData.MINIMIZE -> Connective.MINIMIZE
                ConnectiveData.MAXIMIZE -> Connective.MAXIMIZE
                ConnectiveData.IS_VIOLATED -> Connective.IS_VIOLATED
                ConnectiveData.WHEN -> Connective.WHEN
                ConnectiveData.ALWAYS -> Connective.ALWAYS
                ConnectiveData.SOMETIME -> Connective.SOMETIME
                ConnectiveData.WITHIN -> Connective.WITHIN
                ConnectiveData.AT_MOST_ONCE -> Connective.AT_MOST_ONCE
                ConnectiveData.SOMETIME_AFTER -> Connective.SOMETIME_AFTER
                ConnectiveData.SOMETIME_BEFORE -> Connective.SOMETIME_BEFORE
                ConnectiveData.ALWAYS_WITHIN -> Connective.ALWAYS_WITHIN
                ConnectiveData.HOLD_DURING -> Connective.HOLD_DURING
                ConnectiveData.HOLD_AFTER -> Connective.HOLD_AFTER
                ConnectiveData.AFTER -> Connective.AFTER
                ConnectiveData.FUNCTION_TERM -> Connective.FUNCTION_TERM
                ConnectiveData.TRUE -> Connective.TRUE
                ConnectiveData.FALSE -> Connective.FALSE
                ConnectiveData.HOLD_BEFORE -> Connective.HOLD_BEFORE
                ConnectiveData.BEFORE -> Connective.BEFORE
                ConnectiveData.HOLD_BETWEEN -> Connective.HOLD_BETWEEN
                ConnectiveData.ALIAS_SET -> Connective.ALIAS_SET
                ConnectiveData.TASK -> Connective.TASK
                ConnectiveData.EXPANSION -> Connective.EXPANSION
                else -> null
            }
        }

        private fun data2BitOp(bitOpData: BitOpData): BitOp {
            val result = BitOp(
                bitOpData.name,
                bitOpData.parameters?.size!!
            )
            result.apply {
                this.preconditions = data2BitExp(bitOpData.preconditions!!)
                this.duration = bitOpData.duration!!
                this.cost = bitOpData.cost!!
            }
            bitOpData.effects?.map {
                data2CondBitExp(it)
            }?.forEach {
                result.addCondBitEffect(it)
            }
            // Hack using reflections
            val javaClass = AbstractCodedOp::class.java
            overwritePrivateField(javaClass, result, bitOpData.instantiations?.toIntArray(), "instantiations")
            overwritePrivateField(javaClass, result, bitOpData.parameters?.toIntArray(), "parameters")
            overwritePrivateField(javaClass, result, bitOpData.dummy, "dummy")
            return result
        }

        private fun data2CondBitExp(condBitExpData: CondBitExpData): CondBitExp {
            return CondBitExp(
                data2BitExp(condBitExpData.conditions!!),
                data2BitExp(condBitExpData.effects!!)
            )
        }

        private fun data2BitExp(bitExpData: BitExpData): BitExp {
            return BitExp(
                data2BitVector(bitExpData.positive!!),
                data2BitVector(bitExpData.negative!!)
            )
        }

        private fun data2BitVector(bitVectorData: BitVectorData): BitVector {
            val array = bitVectorData.toLongArray()
            val bitVector = BitVector()
            overwritePrivateField(BitSet::class.java, bitVector, array, "words")
            overwritePrivateField(BitSet::class.java, bitVector, array.size, "wordsInUse")
            return bitVector
        }

        private fun intExp2Data(intExp: IntExp): IntExpData {
            return IntExpData(
                connective = connective2Data(intExp.connective),
                predicate = intExp.predicate,
                arguments = intExp.arguments.toList(),
                children = intExp.children.map {
                    intExp2Data(it)
                },
                variable = intExp.variable,
                type = intExp.type,
                value = intExp.value
            )
        }

        private fun connective2Data(connective: Connective): ConnectiveData {
            return when (connective) {
                Connective.ATOM -> ConnectiveData.ATOM
                Connective.EQUAL_ATOM -> ConnectiveData.EQUAL_ATOM
                Connective.NOT -> ConnectiveData.NOT
                Connective.AND -> ConnectiveData.AND
                Connective.OR -> ConnectiveData.OR
                Connective.FORALL -> ConnectiveData.FORALL
                Connective.EXISTS -> ConnectiveData.EXISTS
                Connective.F_EXP -> ConnectiveData.F_EXP
                Connective.F_EXP_T -> ConnectiveData.F_EXP_T
                Connective.TIME_VAR -> ConnectiveData.TIME_VAR
                Connective.FN_ATOM -> ConnectiveData.FN_ATOM
                Connective.FN_HEAD -> ConnectiveData.FN_HEAD
                Connective.DURATION_ATOM -> ConnectiveData.DURATION_ATOM
                Connective.LESS -> ConnectiveData.LESS
                Connective.LESS_OR_EQUAL -> ConnectiveData.LESS_OR_EQUAL
                Connective.EQUAL -> ConnectiveData.EQUAL
                Connective.GREATER -> ConnectiveData.GREATER
                Connective.GREATER_OR_EQUAL -> ConnectiveData.GREATER_OR_EQUAL
                Connective.MUL -> ConnectiveData.MUL
                Connective.DIV -> ConnectiveData.DIV
                Connective.MINUS -> ConnectiveData.MINUS
                Connective.UMINUS -> ConnectiveData.UMINUS
                Connective.PLUS -> ConnectiveData.PLUS
                Connective.NUMBER -> ConnectiveData.NUMBER
                Connective.ASSIGN -> ConnectiveData.ASSIGN
                Connective.INCREASE -> ConnectiveData.INCREASE
                Connective.DECREASE -> ConnectiveData.DECREASE
                Connective.SCALE_UP -> ConnectiveData.SCALE_UP
                Connective.SCALE_DOWN -> ConnectiveData.SCALE_DOWN
                Connective.AT_START -> ConnectiveData.AT_START
                Connective.AT_END -> ConnectiveData.AT_END
                Connective.OVER_ALL -> ConnectiveData.OVER_ALL
                Connective.MINIMIZE -> ConnectiveData.MINIMIZE
                Connective.MAXIMIZE -> ConnectiveData.MAXIMIZE
                Connective.IS_VIOLATED -> ConnectiveData.IS_VIOLATED
                Connective.WHEN -> ConnectiveData.WHEN
                Connective.ALWAYS -> ConnectiveData.ALWAYS
                Connective.SOMETIME -> ConnectiveData.SOMETIME
                Connective.WITHIN -> ConnectiveData.WITHIN
                Connective.AT_MOST_ONCE -> ConnectiveData.AT_MOST_ONCE
                Connective.SOMETIME_AFTER -> ConnectiveData.SOMETIME_AFTER
                Connective.SOMETIME_BEFORE -> ConnectiveData.SOMETIME_BEFORE
                Connective.ALWAYS_WITHIN -> ConnectiveData.ALWAYS_WITHIN
                Connective.HOLD_DURING -> ConnectiveData.HOLD_DURING
                Connective.HOLD_AFTER -> ConnectiveData.HOLD_AFTER
                Connective.AFTER -> ConnectiveData.AFTER
                Connective.FUNCTION_TERM -> ConnectiveData.FUNCTION_TERM
                Connective.TRUE -> ConnectiveData.TRUE
                Connective.FALSE -> ConnectiveData.FALSE
                Connective.HOLD_BEFORE -> ConnectiveData.HOLD_BEFORE
                Connective.BEFORE -> ConnectiveData.BEFORE
                Connective.HOLD_BETWEEN -> ConnectiveData.HOLD_BETWEEN
                Connective.ALIAS_SET -> ConnectiveData.ALIAS_SET
                Connective.TASK -> ConnectiveData.TASK
                Connective.EXPANSION -> ConnectiveData.EXPANSION
            }
        }

        private fun inertia2Data(inertia: Inertia): InertiaData {
            return when (inertia) {
                Inertia.POSITIVE -> InertiaData.POSITIVE
                Inertia.NEGATIVE -> InertiaData.NEGATIVE
                Inertia.FLUENT -> InertiaData.FLUENT
                Inertia.INERTIA -> InertiaData.INERTIA
            }
        }

        private fun bitOp2Data(bitOp: BitOp): BitOpData {
            return BitOpData(
                preconditions = bitExp2Data(bitOp.preconditions),
                effects = bitOp.condEffects.map {
                    condBitExp2Data(it)
                },
                name = bitOp.name,
                parameters = bitOp.parameters.toList(),
                instantiations = bitOp.instantiations.toList(),
                dummy = bitOp.isDummy,
                cost = bitOp.cost,
                duration = bitOp.duration
            )
        }

        private fun condBitExp2Data(condBitExp: CondBitExp): CondBitExpData {
            return CondBitExpData(
                conditions = bitExp2Data(condBitExp.condition),
                effects = bitExp2Data(condBitExp.effects)
            )
        }

        private fun bitExp2Data(bitExp: BitExp): BitExpData {
            return BitExpData(
                negative = createBitVectorData(bitExp.negative.toLongArray()),
                positive = createBitVectorData(bitExp.positive.toLongArray())
            )
        }

        private fun data2DerivedPredicate(derivedPredicateData: DerivedPredicateData?): DerivedPredicate? {
            return when (derivedPredicateData) {
                null -> null
                else -> DerivedPredicate(
                    data2NamedTypedList(derivedPredicateData.head),
                    data2exp(derivedPredicateData.body)
                )
            }
        }

        private fun data2Method(methodData: MethodData?): Method? {
            return when (methodData) {
                null -> null
                else -> Method(
                    data2symbol(methodData.name),
                    methodData.parameters?.map {
                        data2typedSymbol(it)
                    },
                    data2exp(methodData.tasks),
                    data2exp(methodData.constraints)
                )
            }
        }

        private fun data2Op(opData: OpData?): Op? {
            return when (opData) {
                null -> null
                else -> Op(
                    data2symbol(opData.name),
                    opData.parameters?.map {
                        data2typedSymbol(it)
                    },
                    data2exp(opData.preconditions),
                    data2exp(opData.effects),
                    data2exp(opData.duration)
                )
            }
        }

        private fun data2RequireKey(requireKeyData: RequireKeyData): RequireKey {
            return when (requireKeyData) {
                RequireKeyData.STRIPS -> RequireKey.STRIPS
                RequireKeyData.TYPING -> RequireKey.TYPING
                RequireKeyData.NEGATIVE_PRECONDITIONS -> RequireKey.NEGATIVE_PRECONDITIONS
                RequireKeyData.DISJUNCTIVE_PRECONDITIONS -> RequireKey.DISJUNCTIVE_PRECONDITIONS
                RequireKeyData.EQUALITY -> RequireKey.EQUALITY
                RequireKeyData.EXISTENTIAL_PRECONDITIONS -> RequireKey.EXISTENTIAL_PRECONDITIONS
                RequireKeyData.UNIVERSAL_PRECONDITIONS -> RequireKey.UNIVERSAL_PRECONDITIONS
                RequireKeyData.QUANTIFIED_PRECONDITIONS -> RequireKey.QUANTIFIED_PRECONDITIONS
                RequireKeyData.CONDITIONAL_EFFECTS -> RequireKey.CONDITIONAL_EFFECTS
                RequireKeyData.FLUENTS -> RequireKey.FLUENTS
                RequireKeyData.NUMERIC_FLUENTS -> RequireKey.NUMERIC_FLUENTS
                RequireKeyData.OBJECT_FLUENTS -> RequireKey.OBJECT_FLUENTS
                RequireKeyData.GOAL_UTILITIES -> RequireKey.GOAL_UTILITIES
                RequireKeyData.ACTION_COSTS -> RequireKey.ACTION_COSTS
                RequireKeyData.ADL -> RequireKey.ADL
                RequireKeyData.DURATIVE_ACTIONS -> RequireKey.DURATIVE_ACTIONS
                RequireKeyData.DERIVED_PREDICATES -> RequireKey.DERIVED_PREDICATES
                RequireKeyData.TIMED_INITIAL_LITERALS -> RequireKey.TIMED_INITIAL_LITERALS
                RequireKeyData.PREFERENCES -> RequireKey.PREFERENCES
                RequireKeyData.CONSTRAINTS -> RequireKey.CONSTRAINTS
                RequireKeyData.CONTINOUS_EFFECTS -> RequireKey.CONTINOUS_EFFECTS
                RequireKeyData.DURATION_INEQUALITIES -> RequireKey.DURATION_INEQUALITIES
                RequireKeyData.HTN -> RequireKey.HTN
            }
        }

        private fun data2NamedTypedList(namedTypedListData: NamedTypedListData?): NamedTypedList? {
            return when (namedTypedListData) {
                null -> null
                else -> {
                    val result = NamedTypedList(data2symbol(namedTypedListData.name))
                    namedTypedListData.arguments?.map {
                        data2typedSymbol(it)
                    }?.forEach {
                        result.add(it)
                    }
                    namedTypedListData.types?.forEach {
                        result.addType(data2symbol(it))
                    }
                    return result
                }
            }
        }

        private fun data2typedSymbol(typedSymbolData: TypedSymbolData?): TypedSymbol? {
            return when (typedSymbolData) {
                null -> null
                else -> {
                    val result = TypedSymbol(
                        Symbol(
                            data2SymbolKind(typedSymbolData.kind!!),
                            typedSymbolData.image
                        )
                    )
                    typedSymbolData.types?.map {
                        data2symbol(it)
                    }?.forEach {
                        result.addType(it)
                    }
                    return result
                }
            }
        }

        private fun data2symbol(symbolData: SymbolData?): Symbol? {
            return when (symbolData) {
                null -> null
                else -> {
                    return Symbol(data2SymbolKind(symbolData.kind!!), symbolData.image)
                }
            }
        }

        private fun data2SymbolKind(symbolKindData: SymbolKindData): Symbol.Kind {
            return when (symbolKindData) {
                SymbolKindData.PREDICATE -> Symbol.Kind.PREDICATE
                SymbolKindData.TYPE -> Symbol.Kind.TYPE
                SymbolKindData.ACTION -> Symbol.Kind.ACTION
                SymbolKindData.METHOD -> Symbol.Kind.METHOD
                SymbolKindData.TASK -> Symbol.Kind.TASK
                SymbolKindData.ALIAS -> Symbol.Kind.ALIAS
                SymbolKindData.PREFERENCE -> Symbol.Kind.PREFERENCE
                SymbolKindData.FUNCTOR -> Symbol.Kind.FUNCTOR
                SymbolKindData.VARIABLE -> Symbol.Kind.VARIABLE
                SymbolKindData.DURATION_VARIABLE -> Symbol.Kind.DURATION_VARIABLE
                SymbolKindData.CONTINUOUS_VARIABLE -> Symbol.Kind.CONTINUOUS_VARIABLE
                SymbolKindData.CONSTANT -> Symbol.Kind.CONSTANT
                SymbolKindData.DOMAIN -> Symbol.Kind.DOMAIN
                SymbolKindData.PROBLEM -> Symbol.Kind.PROBLEM
            }
        }

        private fun data2exp(expData: ExpData?): Exp? {
            return when (expData) {
                null -> null
                else -> {
                    val result = Exp(data2Connective(expData.connective))
                    result.apply {
                        this.atom = expData.atom?.map {
                            data2symbol(it)
                        }
                        this.prefName = data2symbol(expData.prefName)
                        this.variables = expData.variables?.map {
                            data2typedSymbol(it)
                        }
                        this.variable = data2symbol(expData.variable)
                    }
                    if (expData.value != null) {
                        result.value = expData.value
                    }
                    expData.children?.map {
                        data2exp(it)
                    }?.forEach {
                        result.addChild(it)
                    }
                    return result
                }
            }
        }

        private fun derivedPredicate2Data(derivedPredicate: DerivedPredicate): DerivedPredicateData {
            return DerivedPredicateData(
                head = namedTypedList2Data(derivedPredicate.head),
                body = exp2Data(derivedPredicate.body)
            )
        }

        private fun op2Data(op: Op): OpData {
            return OpData(
                name = symbol2Data(op.name),
                parameters = op.parameters.map {
                    typedSymbol2Data(it)
                },
                preconditions = exp2Data(op.preconditions),
                effects = exp2Data(op.effects),
                duration = exp2Data(op.duration)
            )
        }

        private fun method2Data(method: Method): MethodData {
            return MethodData(
                name = symbol2Data(method.name),
                parameters = method.parameters.map {
                    typedSymbol2Data(it)
                },
                tasks = exp2Data(method.tasks),
                constraints = exp2Data(method.constraints)
            )
        }

        private fun exp2Data(exp: Exp?): ExpData? {
            if (exp != null) {
                return ExpData().apply {
                    this.connective = connective2Data(exp.connective)
                    this.variables = exp.variables?.map {
                        typedSymbol2Data(it)
                    }
                    this.atom = exp.atom?.map {
                        symbol2Data(it)!!
                    }
                    this.children = exp.children?.map {
                        exp2Data(it)!!
                    }
                    this.value = exp.value
                    this.prefName = symbol2Data(exp.prefName)
                }
            } else {
                return null
            }
        }

        private fun namedTypedList2Data(namedTypedList: NamedTypedList): NamedTypedListData {
            return NamedTypedListData(
                name = symbol2Data(namedTypedList.name),
                arguments = namedTypedList.arguments.map {
                    typedSymbol2Data(it)
                },
                types = namedTypedList.types.map {
                    symbol2Data(it)!!
                }
            )
        }


        private fun typedSymbol2Data(typedSymbol: TypedSymbol): TypedSymbolData {
            return TypedSymbolData(
                types = typedSymbol.types?.map {
                    symbol2Data(it)!!
                },
                kind = symbolKind2Data(typedSymbol.kind),
                image = typedSymbol.image
            )
        }

        private fun symbol2Data(symbol: Symbol?): SymbolData? {
            return when (symbol) {
                null -> null
                else -> SymbolData(
                    kind = symbolKind2Data(symbol.kind),
                    image = symbol.image
                )
            }
        }

        private fun symbolKind2Data(symbolKind: Symbol.Kind): SymbolKindData {
            return when (symbolKind) {
                Symbol.Kind.PREDICATE -> SymbolKindData.PREDICATE
                Symbol.Kind.TYPE -> SymbolKindData.TYPE
                Symbol.Kind.ACTION -> SymbolKindData.ACTION
                Symbol.Kind.METHOD -> SymbolKindData.METHOD
                Symbol.Kind.TASK -> SymbolKindData.TASK
                Symbol.Kind.ALIAS -> SymbolKindData.ALIAS
                Symbol.Kind.PREFERENCE -> SymbolKindData.PREFERENCE
                Symbol.Kind.FUNCTOR -> SymbolKindData.FUNCTOR
                Symbol.Kind.VARIABLE -> SymbolKindData.VARIABLE
                Symbol.Kind.DURATION_VARIABLE -> SymbolKindData.DURATION_VARIABLE
                Symbol.Kind.CONTINUOUS_VARIABLE -> SymbolKindData.CONTINUOUS_VARIABLE
                Symbol.Kind.CONSTANT -> SymbolKindData.CONSTANT
                Symbol.Kind.DOMAIN -> SymbolKindData.DOMAIN
                Symbol.Kind.PROBLEM -> SymbolKindData.PROBLEM
            }
        }

        private fun requireKey2Data(requireKey: RequireKey): RequireKeyData {
            return when (requireKey) {
                RequireKey.STRIPS -> RequireKeyData.STRIPS
                RequireKey.TYPING -> RequireKeyData.TYPING
                RequireKey.NEGATIVE_PRECONDITIONS -> RequireKeyData.NEGATIVE_PRECONDITIONS
                RequireKey.DISJUNCTIVE_PRECONDITIONS -> RequireKeyData.DISJUNCTIVE_PRECONDITIONS
                RequireKey.EQUALITY -> RequireKeyData.EQUALITY
                RequireKey.EXISTENTIAL_PRECONDITIONS -> RequireKeyData.EXISTENTIAL_PRECONDITIONS
                RequireKey.UNIVERSAL_PRECONDITIONS -> RequireKeyData.UNIVERSAL_PRECONDITIONS
                RequireKey.QUANTIFIED_PRECONDITIONS -> RequireKeyData.QUANTIFIED_PRECONDITIONS
                RequireKey.CONDITIONAL_EFFECTS -> RequireKeyData.CONDITIONAL_EFFECTS
                RequireKey.FLUENTS -> RequireKeyData.FLUENTS
                RequireKey.NUMERIC_FLUENTS -> RequireKeyData.NUMERIC_FLUENTS
                RequireKey.OBJECT_FLUENTS -> RequireKeyData.OBJECT_FLUENTS
                RequireKey.GOAL_UTILITIES -> RequireKeyData.GOAL_UTILITIES
                RequireKey.ACTION_COSTS -> RequireKeyData.ACTION_COSTS
                RequireKey.ADL -> RequireKeyData.ADL
                RequireKey.DURATIVE_ACTIONS -> RequireKeyData.DURATIVE_ACTIONS
                RequireKey.DERIVED_PREDICATES -> RequireKeyData.DERIVED_PREDICATES
                RequireKey.TIMED_INITIAL_LITERALS -> RequireKeyData.TIMED_INITIAL_LITERALS
                RequireKey.PREFERENCES -> RequireKeyData.PREFERENCES
                RequireKey.CONSTRAINTS -> RequireKeyData.CONSTRAINTS
                RequireKey.CONTINOUS_EFFECTS -> RequireKeyData.CONTINOUS_EFFECTS
                RequireKey.DURATION_INEQUALITIES -> RequireKeyData.DURATION_INEQUALITIES
                RequireKey.HTN -> RequireKeyData.HTN
            }
        }

        private fun <T, V> overwritePrivateField(javaClass: Class<T>, writeObj: T, readValue: V, fieldName: String) {
            val field: Field = javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(writeObj, readValue)
        }

        private fun <T, R> getPrivateField(javaClass: Class<T>, fieldName: String, obj: T): R {
            val field: Field = javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            // FIXME: cast to R can throw exceptions!
            val value: R = field.get(obj) as R
            return value
        }

        fun createBitVectorData(array: LongArray): BitVectorData {
            val bitVectorData = BitVectorData()
            overwritePrivateField(BitSet::class.java, bitVectorData, array, "words")
            overwritePrivateField(BitSet::class.java, bitVectorData, array.size, "wordsInUse")
            return bitVectorData
        }
    }
}