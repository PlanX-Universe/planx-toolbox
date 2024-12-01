package org.planx.common.models.transforming.converting.encoding

import org.planx.common.models.transforming.converting.ConvertingRequest
import org.planx.common.models.endpoint.solving.domain.Domain
import org.planx.common.models.endpoint.solving.problem.Problem

interface EncodingRequest<P, D> : ConvertingRequest
        where P : Problem, D : Domain {
    var problem: P?
    var domain: D?
}
