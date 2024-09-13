package br.com.jelsonjr.infra.security

import br.com.jelsonjr.infra.config.JwtConfig
import br.com.jelsonjr.infra.errors.exceptions.TokenJWTException
import io.smallrye.jwt.build.Jwt
import io.smallrye.jwt.build.JwtClaimsBuilder
import jakarta.enterprise.context.ApplicationScoped
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Duration
import java.util.Base64

@ApplicationScoped
class JwtUtils(private val jwtConfig: JwtConfig) {

    fun generateToken(username: String, roles: Set<String>): String {
        try {
            val claimsBuilder: JwtClaimsBuilder = Jwt.claims()
                .expiresIn(Duration.ofDays(365))
                .subject(username)
                .issuer(jwtConfig.issuer())
                .groups(roles)

            return claimsBuilder.sign(privateKey())
        } catch (ex: Exception) {
            throw TokenJWTException("Error generating JWT Token: ${ex.message}")
        }
    }

    private fun privateKey(): PrivateKey {
        val classLoader = Thread.currentThread().contextClassLoader
        val privateKeyStream = classLoader.getResourceAsStream(jwtConfig.privateKeyLocation())
            ?: throw TokenJWTException("Error generating JWT Token, private key not found.")

        val keyBytes = privateKeyStream.readBytes()
        val keyString = String(keyBytes)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decodedKey = Base64.getDecoder().decode(keyString)
        val keySpec = PKCS8EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")

        return keyFactory.generatePrivate(keySpec)
    }
}
