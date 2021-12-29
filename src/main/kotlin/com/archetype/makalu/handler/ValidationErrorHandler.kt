package com.archetype.makalu.handler

import io.grpc.Status
import mu.KotlinLogging
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice
import javax.validation.ValidationException

@GRpcServiceAdvice
class ValidationErrorHandler {
    // https://github.com/LogNet/grpc-spring-boot-starter#5-error-handling
    @GRpcExceptionHandler
    fun handle(e: ValidationException, scope: GRpcExceptionScope): Status {
        // https://github.com/LogNet/grpc-spring-boot-starter/blob/master/grpc-spring-boot-starter/src/main/java/org/lognet/springboot/grpc/recovery/ErrorHandlerAdapter.java
        logger.warn("Got error with status ${e.message}")

        return Status.INVALID_ARGUMENT.withDescription(e.message)
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
