package WM.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Your JWT logic here (validate the token, etc.)
        filterChain.doFilter(request, response); // Continue the filter chain
    }
    public static SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);  // Generates a 256-bit key for HS256
    }
}
