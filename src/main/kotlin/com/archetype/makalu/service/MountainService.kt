package com.archetype.makalu.service

import com.archetype.makalu.proto.hike.HighestPoint
import com.archetype.makalu.proto.hike.MountainRequest
import com.archetype.makalu.proto.hike.MountainResponse
import com.archetype.makalu.proto.hike.MountainServiceGrpcKt
import kotlinx.coroutines.delay
import mu.KotlinLogging
import org.lognet.springboot.grpc.GRpcService
import kotlin.random.Random

@GRpcService
class MountainService : MountainServiceGrpcKt.MountainServiceCoroutineImplBase() {
    override suspend fun getMountainDetail(request: MountainRequest): MountainResponse {
        // delay random
        val sleep = Random(System.currentTimeMillis()).nextLong(50, 200)
        logger.debug("sleep: $sleep milliseconds")
        delay(sleep)

        // mock. return own information
        return MountainResponse.newBuilder()
            .setName("Makalu")
            .setCountry("Nepal")
            .setMountainSystem("Himalayan Range")
            .setHighestPoint(
                HighestPoint.newBuilder()
                    .setElevation("8463 m")
                    .setProminence("2386 m")
                    .setIsolation("17 km")
                    .addAllListing(
                        listOf("Eight-thousander", "Ultra")
                    )
                    .setCoordinates("27.889722, 87.088889")
                    .build()
            ).build()
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
