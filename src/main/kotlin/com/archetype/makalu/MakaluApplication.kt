package com.archetype.makalu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.Environment
import reactor.blockhound.BlockHound

@SpringBootApplication
class MakaluApplication(
	env: Environment
) {
	init {
	    if (env.activeProfiles.none { it.startsWith("prod", ignoreCase = true) }) {
			BlockHound.install()
		}
	}
}

fun main(args: Array<String>) {
	runApplication<MakaluApplication>(*args)
}
