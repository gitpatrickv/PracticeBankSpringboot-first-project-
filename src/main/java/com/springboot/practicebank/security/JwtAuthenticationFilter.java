package com.springboot.practicebank.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //OncePerRequestFilter is used for processing every incoming HTTP request, also executes as part of the filter chain.

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    //Login authentication process takes place first if successful it returns Authentication Object before it executes the code below.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,            //Represents the HTTP request.
                                    @NonNull HttpServletResponse response,          //Represents the HTTP response.
                                    @NonNull FilterChain filterChain)               //Represents the filter chain.
            throws ServletException, IOException {

        String token = jwtTokenProvider.getTokenFromRequest(request);      //The TOKEN is extracted from the HTTP request provided by the CLIENT not the SERVER in the Authorization header. (request)
        //The server validates and extracts information from this token to authenticate the user.

        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){    //if the token is valid

            String username = jwtTokenProvider.getUsername(token);                          //extracts username from the payload encoded in BASE64 format.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);     //extracts user details in the database and used it for authentication and authorization.

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                    //After successful login the server will return an Authentication Object that encapsulates information about the authenticated user
                    //including Principal , Credentials, Authority(ROLES)
            );
            //the SERVER will generate a TOKEN based on the authenticated user details and serves as proof of authentication. (response)

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //sets additional HTTP request info from the client into the Authentication Object including IP add, session ID and request headers
            //and stored the Authentication Object in the security context holder to retrieve user info as needed for authentication for the current session.
        }
        filterChain.doFilter(request, response);
    }


    }


