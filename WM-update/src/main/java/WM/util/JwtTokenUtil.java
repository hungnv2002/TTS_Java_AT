package WM.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class JwtTokenUtil {

    public static SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);  // Generates a 256-bit key for HS256
    }
}

