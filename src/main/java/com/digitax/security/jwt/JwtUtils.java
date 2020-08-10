package com.digitax.security.jwt;

import com.digitax.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${digitax.app.jwtSecret}")
    private String jwtSecret;

    @Value("${digitax.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Method will generate JWT token.
     *
     * <p>JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact
     * and self-contained way for securely transmitting information between parties
     * as a JSON object.</p>
     *
     * @param authentication Represents the token for an authentication request or for an
     *                       authenticated principal once the request has been processed by the
     *                       AuthenticationManager.authenticate(Authentication) method.
     * @return JWT token
     * @see <a href="https://jwt.io/introduction/">https://jwt.io/introduction/</a>
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String id = String.valueOf(userPrincipal.getId());
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Method will extract username from JWT.
     *
     * @param token JWT token to extract user id from
     * @return User id value
     */
    public String getUserIdFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validate JWT claims by checking signature, structure, expiration or empty claims. General
     * JWT structure consist of three parts separated by dots (.), which are:
     * --Header
     * --Payload
     * --Signature
     *
     * @param token JWT token to extract username from
     * @return True if JWT token is valid, otherwise false
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}