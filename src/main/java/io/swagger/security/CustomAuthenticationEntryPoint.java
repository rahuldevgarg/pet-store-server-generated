package io.swagger.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component("restAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //resolver.resolveException(request, response, null, exception);
        HashMap<String, String> map = new HashMap<>(2);
        map.put("uri", request.getRequestURI());
        map.put("msg", "Authentication failed");
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        Throwable data = exception.getCause();
        String message = exception.getMessage();
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> statusResponse = new HashMap<>();
        if (exception instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            statusResponse.put("status_code", HttpServletResponse.SC_UNAUTHORIZED);
        } else if(exception instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            statusResponse.put("status_code", HttpServletResponse.SC_FORBIDDEN);
        }
        error.put("response",exception.getMessage());
        statusResponse.put("error", error);
        res.put("status", statusResponse);

        String resBody = objectMapper.writeValueAsString(res);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}

