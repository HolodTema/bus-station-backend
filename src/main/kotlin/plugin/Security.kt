package com.example.plugin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.principal
import java.util.Date

fun Application.configureJWT() {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt {
            realm = jwtRealm

            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .build()
            )

            validate { credential ->
                val userId = credential.payload.getClaim("userId").asInt()
                val role = credential.payload.getClaim("role").asString()
                if (userId == null || role.isNullOrBlank()) {
                    return@validate null
                }

                return@validate JWTPrincipal(credential.payload)
            }
        }
    }
}

fun Application.generateToken(userId: Int, role: String): String {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()

    return JWT.create()
        .withIssuer(jwtIssuer)
        .withClaim("userId", userId)
        .withClaim("role", role)
        .withExpiresAt(Date(System.currentTimeMillis() + 86_400_000))
        .sign(Algorithm.HMAC256(jwtSecret))
}

fun ApplicationCall.getUserId(): Int {
    val principal = principal<JWTPrincipal>()

    return principal?.payload?.getClaim("userId")?.asInt()
        ?: throw IllegalArgumentException("No userId in this token.")
}

fun ApplicationCall.getRole(): String {
    val principal = principal<JWTPrincipal>()
    return principal?.payload?.getClaim("role")?.asString()
        ?: throw IllegalArgumentException("No role in this token.")
}
