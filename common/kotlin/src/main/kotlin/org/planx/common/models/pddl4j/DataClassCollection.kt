package org.planx.common.models.pddl4j

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.util.*


/**
 * wrapper for [fr.uga.pddl4j.util.BitExp]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class BitExpData(
    var positive: BitVectorData? = null,
    var negative: BitVectorData? = null
)

/**
 * wrapper for [fr.uga.pddl4j.util.BitOp]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class BitOpData(
    var preconditions: BitExpData? = null,
    var effects: List<CondBitExpData>? = null,
    var name: String = "",
    var parameters: List<Int>? = null,
    var instantiations: List<Int>? = null,
    var dummy: Boolean = false,
    var cost: Double? = null,
    var duration: Double? = null
)


/**
 * wrapper for [fr.uga.pddl4j.util.CondBitExp]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class CondBitExpData(
    var conditions: BitExpData? = null,
    var effects: BitExpData? = null
)

/**
 * wrapper for [fr.uga.pddl4j.util.BitVector]
 */
@JsonSerialize(using = BitSetSerializer::class)
@JsonDeserialize(using = BitSetDeserializer::class)
class BitVectorData : BitSet()

/**
 * wrapper for [fr.uga.pddl4j.util.IntExp]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class IntExpData(
    var connective: ConnectiveData? = null,
    var predicate: Int? = null,
    var arguments: List<Int>? = null,
    var children: List<IntExpData>? = null,
    var variable: Int? = null,
    var type: Int? = null,
    var value: Double? = null
)

/**
 * wrapper for [fr.uga.pddl4j.parser.Connective]
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class ConnectiveData {
    ATOM,
    EQUAL_ATOM,
    NOT,
    AND,
    OR,
    FORALL,
    EXISTS,
    F_EXP,
    F_EXP_T,
    TIME_VAR,
    FN_ATOM,
    FN_HEAD,
    DURATION_ATOM,
    LESS,
    LESS_OR_EQUAL,
    EQUAL,
    GREATER,
    GREATER_OR_EQUAL,
    MUL,
    DIV,
    MINUS,
    UMINUS,
    PLUS,
    NUMBER,
    ASSIGN,
    INCREASE,
    DECREASE,
    SCALE_UP,
    SCALE_DOWN,
    AT_START,
    AT_END,
    OVER_ALL,
    MINIMIZE,
    MAXIMIZE,
    IS_VIOLATED,
    WHEN,
    ALWAYS,
    SOMETIME,
    WITHIN,
    AT_MOST_ONCE,
    SOMETIME_AFTER,
    SOMETIME_BEFORE,
    ALWAYS_WITHIN,
    HOLD_DURING,
    HOLD_AFTER,
    AFTER,
    FUNCTION_TERM,
    TRUE,
    FALSE,
    HOLD_BEFORE,
    BEFORE,
    HOLD_BETWEEN,
    ALIAS_SET,
    TASK,
    EXPANSION,
}

/**
 * wrapper for [fr.uga.pddl4j.encoding.Inertia]
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class InertiaData {
    POSITIVE,
    NEGATIVE,
    INERTIA,
    FLUENT
}

/**
 * wrapper for [fr.uga.pddl4j.parser.TypedSymbol]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class TypedSymbolData(
    var types: List<SymbolData>? = null,
    override var kind: SymbolKindData? = null,
    override var image: String? = ""
) : SymbolData()

/**
 * wrapper for [fr.uga.pddl4j.*]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
open class SymbolData(
    open var kind: SymbolKindData? = null,
    open var image: String? = null
)

@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class SymbolKindData {
    /**
     * The predicate symbol.
     */
    PREDICATE,

    /**
     * The type symbol.
     */
    TYPE,

    /**
     * The action symbol.
     */
    ACTION,

    /**
     * The method symbol.
     */
    METHOD,

    /**
     * The task symbol.
     */
    TASK,

    /**
     * The alias symbol.
     */
    ALIAS,

    /**
     * The preference symbol.
     */
    PREFERENCE,

    /**
     * The functor symbol.
     */
    FUNCTOR,

    /**
     * The variable symbol.
     */
    VARIABLE,

    /**
     * The duration variable symbol.
     */
    DURATION_VARIABLE,

    /**
     * The continuous variable symbol.
     */
    CONTINUOUS_VARIABLE,

    /**
     * The constant symbol.
     */
    CONSTANT,

    /**
     * The domain symbol.
     */
    DOMAIN,

    /**
     * The problem symbol.
     */
    PROBLEM
}


@JsonFormat(shape = JsonFormat.Shape.STRING)
enum class RequireKeyData {
    STRIPS,
    TYPING,
    NEGATIVE_PRECONDITIONS,
    DISJUNCTIVE_PRECONDITIONS,
    EQUALITY,
    EXISTENTIAL_PRECONDITIONS,
    UNIVERSAL_PRECONDITIONS,
    QUANTIFIED_PRECONDITIONS,
    CONDITIONAL_EFFECTS,
    FLUENTS,
    NUMERIC_FLUENTS,
    OBJECT_FLUENTS,
    GOAL_UTILITIES,
    ACTION_COSTS,
    ADL,
    DURATIVE_ACTIONS,
    DERIVED_PREDICATES,
    TIMED_INITIAL_LITERALS,
    PREFERENCES,
    CONSTRAINTS,
    CONTINOUS_EFFECTS,
    DURATION_INEQUALITIES,
    HTN
}


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class NamedTypedListData(
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    var name: SymbolData? = null,
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    var arguments: List<TypedSymbolData>? = emptyList(),
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    var types: List<SymbolData>? = emptyList()
)

/**
 * wrapper for [fr.uga.pddl4j.parser.Exp]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class ExpData(
    var connective: ConnectiveData? = null,
    var variables: List<TypedSymbolData>? = null,
    var variable: SymbolData? = null,
    var atom: List<SymbolData>? = null,
    var children: List<ExpData>? = null,
    var value: Double? = null,
    var prefName: SymbolData? = null
)

/**
 * wrapper for [fr.uga.pddl4j.parser.Method]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class MethodData(
    var name: SymbolData? = null,
    var parameters: List<TypedSymbolData>? = null,
    var tasks: ExpData?,
    var constraints: ExpData?
)

/**
 * wrapper for [fr.uga.pddl4j.parser.Op]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class OpData(
    var name: SymbolData? = null,
    var parameters: List<TypedSymbolData>? = null,
    var preconditions: ExpData? = null,
    var effects: ExpData? = null,
    var duration: ExpData? = null
)

/**
 * wrapper for [fr.uga.pddl4j.parser.DerivedPredicate]
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
data class DerivedPredicateData(
    var head: NamedTypedListData? = null,
    var body: ExpData? = null
)

