package com.ktds.zipzero.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication auth) throws IOException, ServletException {

                log.info("Login Success");
                log.info("Login Success");
        
                log.info("Login Success");
                log.info("Login Success");
        
                List<String> roleNames = new ArrayList<>();
        
                auth.getAuthorities().forEach(authority -> {
        
                    roleNames.add(authority.getAuthority());
        
                });
        
                log.info("======================== roleNames : " + roleNames);
        
                log.warn("ROLE NAMES: " + roleNames);
        
                if (roleNames.contains("ADMIN")) {
        
                    response.sendRedirect("/index");
                    return;
                }
        
                if (roleNames.contains("HEADQUARTER")) {
        
                    response.sendRedirect("/index");
                    return;
                }
        
                if (roleNames.contains("DEPARTMENT")) {
        
                    response.sendRedirect("/index");
                    return;
                }
        
                if (roleNames.contains("TEAM")) {
        
                    response.sendRedirect("/index");
                    return;
                }
        
                if (roleNames.contains("USER")) {
        
                    response.sendRedirect("/index");
                    return;
                }
        
                response.sendRedirect("/index");
            }


}
