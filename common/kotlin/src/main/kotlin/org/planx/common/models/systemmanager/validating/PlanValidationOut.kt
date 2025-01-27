package org.planx.common.models.systemmanager.validating

import org.planx.common.models.endpoint.solving.plan.PlanXAction
import org.planx.common.models.endpoint.solving.plan.sequential.SequentialPlan

data class PlanValidationOut(
    override val isValid: Boolean = false,
    /**
     * This variable contains the full console output stream of the VAL validation command.
     */
    val fullOutput: String? = null,
    /**
     * In some cases VAL can provide customer advice to fix the plan!
     */
    val adviceText: String? = null,

    override var plan: SequentialPlan<PlanXAction>?
) : PlanValidationResponse<SequentialPlan<PlanXAction>>
