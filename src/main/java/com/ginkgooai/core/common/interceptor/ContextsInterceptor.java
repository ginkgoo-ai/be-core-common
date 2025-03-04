package com.ginkgooai.core.common.interceptor;

import com.ginkgooai.core.common.utils.ContextInitializer;
import com.ginkgooai.core.common.utils.ContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

public class ContextsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ContextInitializer.load(request);
        return true;
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if (ContextUtils.get() == null){
            return;
        }
        ContextUtils.get().clearAll();

    }
}
