package movwe.services.authServices;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtService implements Serializable {
    @Value( "${jwt.secret.key}")
    private String SECRET_KEY;
    private final long EXPIRATION_TIME = 1000 * 60 * 1000;    //1000 minutes

    /// Generate a JWT token
    public String generateToken(String email, String username) {
        return JWT.create()
                .withSubject(email)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractEmail(String token) {
        return decodedToken(token).getSubject();
    }

    public String extractUsername(String token) {
        return decodedToken(token).getClaim("username").asString();
    }

    public boolean isTokenExpired(String token) {
        return decodedToken(token).getExpiresAt().before(new Date());
    }

    public boolean validateToken(String token, String email) {
        return (email.equals(extractEmail(token)) && !isTokenExpired(token));
    }

    private DecodedJWT decodedToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }
}
