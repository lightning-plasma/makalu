package com.archetype.makalu.interceptor

import io.grpc.ForwardingServerCall
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.grpc.Status
import mu.KotlinLogging
import org.lognet.springboot.grpc.GRpcGlobalInterceptor

// https://github.com/grpc/grpc-kotlin/issues/141
@GRpcGlobalInterceptor
class ExceptionInterceptor : ServerInterceptor {
    private class ExceptionTranslatingServerCall<ReqT, RespT>(
        delegate: ServerCall<ReqT, RespT>
    ) : ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(delegate) {
        override fun close(status: Status, trailers: Metadata) {
            if (status.isOk) {
                return super.close(status, trailers)
            }
            val cause = status.cause

            logger.error(cause) { "Error handling gRPC endpoint." }

            val newStatus = if (status.code == Status.Code.UNKNOWN) {
                // handling
                val translatedStatus = when (cause) {
                    is IllegalArgumentException -> Status.INVALID_ARGUMENT
                    is IllegalStateException -> Status.FAILED_PRECONDITION
                    else -> Status.UNKNOWN
                }

                translatedStatus.withDescription(cause?.message).withCause(cause)
            } else status

            super.close(newStatus, trailers)
        }
        companion object {
            private val logger = KotlinLogging.logger { }
        }
    }

    override fun <ReqT : Any, RespT : Any> interceptCall(
        call: ServerCall<ReqT, RespT>,
        headers: Metadata,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        return next.startCall(ExceptionTranslatingServerCall(call), headers)
    }
}
