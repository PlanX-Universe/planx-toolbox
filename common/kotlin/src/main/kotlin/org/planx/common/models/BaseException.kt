package org.planx.common.models

abstract class BaseException : Exception() {
    abstract val requestId: String
    abstract var internalErrors: String?
}
