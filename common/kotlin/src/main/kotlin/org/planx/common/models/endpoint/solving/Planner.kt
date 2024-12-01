package org.planx.common.models.endpoint.solving

// FIXME: think about a more generic solution! Probably passing a string is more sufficient!
enum class Planner(val value: String) {
    /**
     * The HSP (Heuristic Search Planner).
     */
    HSP("hsp"),

    /**
     * The FF (Fast Forward Planner).
     */
    FF("ff"),

    /**
     * The FF Anytime (Fast Forward Anytime Planner).
     */
    FFAnytime("ff-anytime"),

    /**
     * The HC Anytime (Hill Climbing Anytime Planner).
     */
    HCAnytime("hc-anytime")
}