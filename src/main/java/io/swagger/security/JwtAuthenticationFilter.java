package io.swagger.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.constant.AuthenticationConstant;
import io.swagger.model.User;
import io.swagger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    private final static UrlPathHelper urlPathHelper = new UrlPathHelper();
    private final JwtUtils jwtUtils = new JwtUtils();
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        try{
            UserDetails creds = new ObjectMapper().readValue(httpServletRequest.getInputStream(), UserDetails.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            roles.add(ga.getAuthority());
        }
        // String token = jwtUtils.generateToken(((User) authentication.getPrincipal()).getUsername());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim(AuthenticationConstant.USERID,((UserDetails) authentication.getPrincipal()).getUsername())
                .withClaim(AuthenticationConstant.ROLES,  roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(AuthenticationConstant.SECRET.getBytes()));
        response.addHeader(AuthenticationConstant.HEADER_STRING, AuthenticationConstant.TOKEN_PREFIX + token);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
//      response.getWriter().write("{\"" + "Error" + "\":\"" + e + "\"}");

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        logger.debug("failed authentication while attempting to access "
                + urlPathHelper.getPathWithinApplication(request));

        //Add more descriptive message
        if (failed instanceof BadCredentialsException) {
            failed = new BadCredentialsException(failed.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        customAuthenticationEntryPoint.commence(request, response, failed);
    }
}

