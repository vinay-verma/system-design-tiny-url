package com.zemoso.systemdesignbootcamp.tinyurlapi.config;

import com.zemoso.systemdesignbootcamp.tinyurlapi.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userEmail = request.getHeader(Constants.HEADER_USER_EMAIL_ID);
        if (StringUtils.isBlank(userEmail)) {
            log.warn("Header {} is missing !! Access restricted to public services only", Constants.HEADER_USER_EMAIL_ID);
            userEmail = Constants.ANONYMOUS_PUBLIC_USER_EMAIL;
        }
        MDC.clear();
        MDC.put(Constants.HEADER_USER_EMAIL_ID, userEmail);
        MDC.put(Constants.REQUEST_ID, UUID.randomUUID().toString());
        CurrentRequestDataHolder.set(CurrentRequestDataHolder.RequestKey.USER_EMAIL, userEmail);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CurrentRequestDataHolder.clear();
        MDC.clear();
    }
}
