package com.testone.test.filters;

import com.testone.test.service.JwtService;
import com.testone.test.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if( authHeader ==null ||!authHeader.startsWith("Bearer ")){
            System.out.println("JwtAuthenticationFilter:1.)=>IS EMPTY CONTINUE WITH FilterChain.doFilter(req,res)");
            filterChain.doFilter(request,response);
            return;
        }
        jwt =authHeader.substring(7);
        log.debug("JWT-{}",jwt.toString());
        userEmail=jwtService.extractUsername(jwt);

        System.out.println("1.)=>JwtAuthenticationFilter:extract email: email"+ userEmail);

        if(!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userService.userDetailsService().loadUserByUsername((userEmail));
            System.out.println("2.)=>JwtAuthenticationFilter:EMAIL NOT EMPTY: userDetails:getUsername" + userDetails.getUsername());
            if(jwtService.isTokenValid(jwt,userDetails)){
                log.debug("JwtAuthentication Filter:token is VALID:_User -{}",userDetails);
                SecurityContext context;
                context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
                filterChain.doFilter(request,response);
                return;
            }
        }

    }
}
