package movwe.utils.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import movwe.services.auth.JwtService;
import movwe.services.auth.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private MyUserDetailsService myUserDetailsService;

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        String token = null;
        String email = null;

        if (request.getRequestURL().toString().contains("/api/")){
            if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
                token = authHeader.substring(7);
                try {
                    email = jwtService.extractEmail(token);
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

                    if (jwtService.validateToken(token, email)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            } else {
                logger.warn("JWT token is missing in request header");
            }
        }

        filterChain.doFilter(request, response);
    }
}
