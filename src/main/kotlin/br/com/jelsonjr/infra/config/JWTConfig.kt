package br.com.jelsonjr.infra.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "jwt")
interface JwtConfig {
    fun issuer(): String
    fun privateKeyLocation(): String
}
