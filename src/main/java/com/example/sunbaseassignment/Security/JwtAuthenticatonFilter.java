package com.example.sunbaseassignment.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Log4j2
@Component
public class JwtAuthenticatonFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelperClass jwtHelper;


    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        Accessing the Bearer
        String requestHeader = request.getHeader("Authorization");
        log.info("Authorization Header: {}", requestHeader);
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            //now we have received a token in the crt format
            token = requestHeader.substring(7);
            log.debug("Extracted Token: {}", token);

            try {
//                getting the username from token provided
                username = this.jwtHelper.getUsernameFromToken(token);
                log.debug("Username extracted from token: {}", username);


            } catch (IllegalArgumentException e) {
                log.error("Illegal Argument while fetching the username from token.", e);
            } catch (ExpiredJwtException e) {
                log.error("JWT token is expired. Token: {}", token, e);
            } catch (MalformedJwtException e) {
                log.error("Invalid JWT token. Token: {}", token, e);
            } catch (Exception e) {
                log.error("An unexpected error occurred while processing the token.", e);
            }
        } else {
            log.warn("Invalid or missing Authorization header. Header value: {}", requestHeader);
        }


        //
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Attempting to authenticate user: {}", username);



            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("User authenticated and security context updated for: {}", username);



            } else {
                logger.info("Validation fails !!");
            }


        }

        filterChain.doFilter(request, response);
    }
}
