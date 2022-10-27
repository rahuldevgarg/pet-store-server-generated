package io.swagger.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //todo your business
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> statusResponse = new HashMap<>();
        statusResponse.put("status_code", HttpServletResponse.SC_FORBIDDEN);
        error.put("response",accessDeniedException.getMessage());
        statusResponse.put("error", error);
        res.put("status", statusResponse);
        // String resBody = objectMapper.writeValueAsString(managerException);
        String resBody = objectMapper.writeValueAsString(res);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}

