package study.realWorld.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //GenericFilterBean을 상속받아 doFilter메소드를 구현하는데 실제 필터링 로직을 작성한다.
    //그리고 doFiler의 역할을 토큰의 인증정보를 SecurityContext에 저장하는 역할을 수행한다.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}",authentication.getName(),requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}",requestURI);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String rawToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(rawToken) && rawToken.startsWith("Token ")) {
            return rawToken.substring("Token ".length());
        }
        return null;
    }
}
