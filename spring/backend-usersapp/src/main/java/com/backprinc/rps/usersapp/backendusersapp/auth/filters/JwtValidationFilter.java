package com.backprinc.rps.usersapp.backendusersapp.auth.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.backprinc.rps.usersapp.backendusersapp.auth.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static com.backprinc.rps.usersapp.backendusersapp.auth.TokenJwtConfig.SECRET_KEY;
import static com.backprinc.rps.usersapp.backendusersapp.auth.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.backprinc.rps.usersapp.backendusersapp.auth.TokenJwtConfig.PREFIX_TOKEN;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header==null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");
        
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token.trim()).getPayload();
            
            String username = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");
            
            System.out.println("username: "+username);
            System.out.println("length: "+username.length());
            Collection<? extends GrantedAuthority> authorities = Arrays
                                      .asList(new ObjectMapper()
                                      .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                                      .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch(JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error",e.getMessage());
            body.put("message", "El token JWT no es valido!");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType("application/json");
        }

    }

    

}
