package fx.github.greys.web.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.collect.Sets;
import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.entity.system.User;
import fx.github.greys.web.service.UserService;
import fx.github.greys.web.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static fx.github.greys.web.constant.Constants.COMMA;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor, InitializingBean {

    @Value("${greys.web.white-urls}")
    private String urls;

    private Set<String> whiteUrls;

    @Autowired
    UserService userService;

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String url = request.getRequestURI();
        for (String white : whiteUrls) {
            if (pathMatcher.match(white, url)) {
                //若在白名单中，不在校验token
                return true;
            }
        }
        //不在白名单中，需要验证token
        if (StringUtils.isBlank(token) || !validateToken(token)) {
            response.getWriter().println(JacksonUtils.toJSon(GreysResponse.createTimeout()));
            return false;
        }
        return true;
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    private boolean validateToken(String token) {
        try {
            //获取 token 中的 user id
            String userId = JWT.decode(token).getAudience().get(0);
            User user = userService.findUserByUsername(userId);
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token);
        } catch (Exception e) {
            log.error("validate token occurs exception.", e);
            return false;
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        String[] urlArr = StringUtils.split(urls, COMMA);
        whiteUrls = Sets.newHashSet(urlArr);
    }
}
