package com.ashok.shopInventory.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


public class WebContextUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContextUtils.class);

    public static HttpServletRequest getRequest() {
        if (null != RequestContextHolder.getRequestAttributes()) {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        return null;
    }

    public static ServletContext getServletContext() {
        return Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext()).getServletContext();
    }

    public static String getCurrentUserEmail() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Anonymous_User";
        }
        return "Anonymous_User";
    }

}


