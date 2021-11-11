package fx.github.greys.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${greys.web.white-urls}")
    private Set<String> whiteUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
