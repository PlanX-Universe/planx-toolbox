package org.planx.common.models

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @param sender of type [String] is the name of the main topic of the current application
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CustomErrorMessage(
        val sender: String = "",
        val requestId: String = "",
        val errorMessage: String? = "",
        val stackTrace: List<StackTraceElement>? = null
)
