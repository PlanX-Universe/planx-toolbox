package org.planx.parsing.exceptions

import org.planx.common.models.BaseException

class ParsingError(
    override val requestId: String = "",
    override var internalErrors: String? = ""
) : BaseException()