package com.calendar.app.security.jwt;

import com.calendar.app.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private static UserDetails UserLoggedIn;

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        try {
            String jwt = this.parserJwt( request );
            if( jwt != null && jwtUtils.validateJwtToken( jwt ) ) {
                String username = jwtUtils.getUserNameFromJwtToken( jwt );

                UserDetails userDetails = this.userDetailsService.loadUserByUsername( username );
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );

                SecurityContextHolder.getContext().setAuthentication( authenticationToken );
                UserLoggedIn = userDetails;
            }
        } catch ( Exception e ) {
            logger.error( "Cannot set user authentication: {}", e );
        }

        filterChain.doFilter( request, response );
    }

     public static UserDetails getUserLoggedIn() {
        return UserLoggedIn;
    }

    public String parserJwt( HttpServletRequest request ) {
        String headerAuth = request.getHeader( "Authorization" );
        return StringUtils.hasText( headerAuth ) ? headerAuth : null;
    }
}