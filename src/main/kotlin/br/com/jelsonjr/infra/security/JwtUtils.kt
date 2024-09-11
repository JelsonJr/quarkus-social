package br.com.jelsonjr.infra.security

import io.smallrye.jwt.build.Jwt
import io.smallrye.jwt.build.JwtClaimsBuilder
import jakarta.enterprise.context.ApplicationScoped
import java.time.Duration

@ApplicationScoped
class JwtUtils {

    companion object {
        private const val ISSUER = "quarkus_social"
        private const val SECRET_KEY = "mysecretkeythatmustbeatleast32characterslong"
    }

    fun generateToken(username: String, roles: Set<String>): String {
        val claimsBuilder: JwtClaimsBuilder = Jwt.claims()
            .expiresIn(Duration.ofDays(365))
            .subject(username)
            .issuer(ISSUER)
            .groups(roles)

        return claimsBuilder.signWithSecret(SECRET_KEY)
    }
}
