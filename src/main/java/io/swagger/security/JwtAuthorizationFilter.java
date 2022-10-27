package io.swagger.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.constant.AuthenticationConstant;
import io.swagger.constant.HeaderConstant;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final static JwtUtils jwtUtils = new JwtUtils();

    @Autowired
    MyUserDetailsService userDetailsService;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String header = httpServletRequest.getHeader(HeaderConstant.AUTHORIZATION);
        if (header == null || !header.startsWith(AuthenticationConstant.TOKEN_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(httpServletRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Collection auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HeaderConstant.AUTHORIZATION);
        //httpServletRequest.getRo
        if (token != null) {
            // String userInformation = jwtUtils.extractUsername(token);
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(AuthenticationConstant.SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .verify(token.replace(AuthenticationConstant.TOKEN_PREFIX, ""));
            String userEmail = decodedJWT.getSubject();
            UserDetails userDetails=userDetailsService.loadUserByUsername(userEmail);
            List<String> auth=  decodedJWT.getClaim(AuthenticationConstant.ROLES).asList(String.class);
            Collection<? extends GrantedAuthority> authR = decodeAuthority(auth);
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authR);
        } else {
            //throw new MissingRequestHeaderException(HeaderConstant.AUTHORIZATION,);
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> decodeAuthority(List<String> rolesString) {
        int len = rolesString.size();
        SimpleGrantedAuthority[] simpleGrantedAuthorities = new SimpleGrantedAuthority[len];
        for(int index =0;index<len;index++){
            simpleGrantedAuthorities[index] = new SimpleGrantedAuthority(rolesString.get(index));
        }
        return Arrays.asList(simpleGrantedAuthorities);
    }
}

